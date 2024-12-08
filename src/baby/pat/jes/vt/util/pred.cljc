(ns baby.pat.jes.vt.util.pred
  (:require [orchestra.core #?(:clj :refer :cljs :refer-macros) [defn-spec]]
            [baby.pat.jes.vt.util.rand-of :as rand-of]
            [clojure.spec.alpha :as s]))

(defn-spec is-rand-from? boolean? [category keyword? s any?]
  (contains? (get rand-of/random-category-map category) s))

(defn-spec unqualified-keyword? boolean?
  "Is a thing a keyword with no namespace."
  [thing any?]
  (and (keyword? thing) (not (qualified-keyword? thing))))

(defn-spec type-of? boolean?
  "Does the type of a thing as a string include the string provided?"
  [thing any? string string?]
  (clojure.string/includes? (str (type thing)) string))

(defn-spec atom? boolean?
  "Is a thing an atom?"
  [thing any?] 
  #?(:clj (type-of? thing "clojure.lang.Atom")
     :cljs (instance? cljs.core/Atom thing)))

(defn-spec all-valid-examples-of? boolean? [things any? vt qualified-keyword?]
  (every? (fn [x] (s/valid? vt x)) things))

(defn-spec contains-keys? boolean?
  "Does a map contain all the provided keys?"
  ([m map? k keyword?]
   (contains? (set (keys m)) k))
  ([m map? k keyword? & ks #(and (coll? %) (every? keyword? %))]
   (every? true? (map #(contains-keys? m %) (flatten [k ks])))))

(defn-spec map-with? boolean?
  "Does a map contain all these keys?"
  [& qkws #(and (coll? %) (every? keyword? %))]
  (s/and map? #(apply contains-keys? (flatten [% qkws]))))


(defn-spec normalizable? boolean?
  "Can the provided map be normalized?"
  [m any?]
  (if-not (map? m)
    false
    (let [x (atom [])]
      (clojure.walk/postwalk #(when (and (qualified-keyword? %) (= "id" (name %)))
                                (swap! x conj :true)) m)
      (when-not (empty? @x) true))))

(defn-spec is-normalized? boolean?
  "Is the provided map normalized or empty?"
  [m any?]
  (if-not (or (map? m) (normalizable? m))
    false
    (let [all-keys-are-id-keys (every? #(= "id" %) (map name (keys m)))
          all-vals-are-maps (every? map? (vals m))
          monster-entity-predicate
          (when (every? true? (map map? (vals m)))
            (every? true?
                    (vals (apply merge
                                 (map #(medley.core/map-kv
                                        (fn [k v]
                                          (let [id-kw
                                                (ffirst (medley.core/filter-keys
                                                         (fn [k] (= "id" (name k))) v))]
                                            [k (if (and (map? v)
                                                        (contains?
                                                         (set (map name (keys v))) "id")
                                                        (= k (get v id-kw)))
                                                 true
                                                 (set (keys v)))])) %) (vals m))))))]
      (or (empty? m)
          (every? true? [monster-entity-predicate all-keys-are-id-keys all-vals-are-maps])))))

(defn-spec entity-ident? boolean?
  "Is a thing an entity id?"
  [thing any?]
  (and (vector? thing)
       (= 2 (count thing))
       (keyword? (first thing))
       (= "id" (name (first thing)))))

(defn-spec entity-idents?  boolean?
  "Is this thing a vector of entity ids?"
  [thing any?]
  (and (vector? thing)
       (every? entity-ident? thing)))

(defn-spec entity-ident-or-idents? boolean?
  "Is this thing an ident or group of them?"
  [thing any?]
  (or (entity-ident? thing) (entity-idents? thing)))

(defn-spec id-qkw? boolean?
  "Is a thing a qualified id keyword?"
  [thing keyword?]
  (= "id" (name thing)))

(ns baby.pat.jes.vt.util
  (:require [baby.pat.jes.vt :as vt]
            [baby.pat.jes.vt.util.inflection :as inflection]
            [baby.pat.jes.vt.util.pred :as pred]
            [baby.pat.jes.vt.util.rand-of :as rand-of]
            [orchestra.core #?(:clj :refer :cljs :refer-macros) [defn-spec]]))

(def singular? inflection/singular?)
(def plural? inflection/plural?)
(def singular inflection/singular)
(def plural inflection/plural)
(def singular-keyword? inflection/singular-keyword?)
(def plural-keyword? inflection/plural-keyword?)
(def singular-keyword inflection/singular-keyword)
(def plural-keyword inflection/plural-keyword)
(def ordinalize inflection/ordinalize)

(def unqualified-keyword? pred/unqualified-keyword?)
(def type-of? pred/type-of?)
(def atom? pred/atom?)
(def all-valid-examples-of? pred/all-valid-examples-of?)
(def contains-keys? pred/contains-keys?)
(def map-with? pred/map-with?)
(def normalizable? pred/normalizable?)
(def is-normalized? pred/is-normalized?)
(def entity-ident? pred/entity-ident?)
(def entity-idents? pred/entity-idents?)
(def entity-ident-or-idents? pred/entity-ident-or-idents?)
(def id-qkw? pred/id-qkw?)

(def random-category-map rand-of/random-category-map)
(def random-categories rand-of/random-categories)
(def rand-of rand-of/rand-of)
(def counts-map rand-of/counts-map)
(def categories-by-count rand-of/categories-by-count)
(def categories-by-least rand-of/categories-by-least)
(def rand-str rand-of/rand-str)
(def qid rand-of/qid)



(defn-spec vt-dispatch ::vt/qkw
  "Takes a thing and returns it's value-type qualified keyword.
   This is a dumber case-based type dispatch."
  [thing ::vt/any]
  (if (fn? thing)
    ::vt/vt/fn
    (cond
      (nil? thing) ::vt/nil
      (vector? thing) ::vt/vec
      (set? thing) ::vt/set
      (map? thing) ::vt/map
      (keyword? thing) ::vt/kw
      (string? thing) ::vt/str
      (integer? thing) ::vt/long
      (double? thing) ::vt/double
      (boolean? thing) ::vt/?
      (pred/type-of? thing "clojure.core.async") ::vt/chan
      :else ::vt/any)))

(defn-spec add-kw-ns ::vt/any
  "Adds a provided namespace to the thing or things provided DWIM style."
  [new-namespace ::vt/kw-or-str thing ::vt/any]
  (let [rethink-this-shit-its-bogus-vt-any-are-you-kidding-me? (println (str "add-kw-ns takes keywords, maps, set, and vectors. You supplied: " thing ". Which is a " (type thing)))]
    (case (vt-dispatch thing)
      ::vt/kw (keyword (name new-namespace) (name thing))
      ::vt/map (medley.core/map-keys (comp #(keyword (name new-namespace) %) name) thing)
      ::vt/set (into #{} (map #(add-kw-ns new-namespace %) thing))
      ::vt/vec (mapv #(add-kw-ns new-namespace %) thing)
      ::vt/any rethink-this-shit-its-bogus-vt-any-are-you-kidding-me?
      :else rethink-this-shit-its-bogus-vt-any-are-you-kidding-me?)))

(defn-spec rm-kw-ns ::vt/any
  "Removes the namespace for a thing or things DWIM style."
  [thing ::vt/any]
  (case (vt-dispatch thing)
    ::vt/kw (keyword (name thing))
    ::vt/map (medley.core/map-keys (comp keyword name) thing)
    ::vt/set (set (map rm-kw-ns thing))
    ::vt/vec (mapv rm-kw-ns thing)
    :else thing))


(defn-spec qkw->relative-path ::vt/str
  [qkw ::vt/qkw]
  (-> (clojure.string/replace (str (namespace qkw) "/"
                                   (name qkw)) #"\." "/")))

(defn-spec kw->relative-path ::vt/str
  [kw ::vt/kw]
  (if (qualified-keyword? kw)
    (qkw->relative-path kw)
    (name kw)))

(defn-spec if-atom-deref ::vt/any
  [thing ::vt/any]
  (if (pred/atom? thing)
    (deref thing)
    thing))

(defn-spec quick-rn-fn ::vt/fn
  "Takes a rename-map and returns a function that renames the keys of a supplied map."
  [rn-map ::vt/map]
  (fn [data]
    (clojure.set/rename-keys data rn-map)))

(defn-spec walk-dissoc ::vt/map
  "Walks a map returning it without the provided keys."
  [m ::vt/map k ::vt/kw & ks ::vt/coll-of-kws]
    (clojure.walk/prewalk
     (fn [v]
       (if (map? v)
         (apply dissoc v (flatten [k ks]))
         v)) m))

(defn-spec callable-fn-from-thing ::vt/fn
  "Given a keyword or a string returns a callable function that thing resolves to."
  [fq ::vt/qkw-or-str]
  #?(:clj (let [split-var (if (string? fq)
                            (clojure.string/split fq #"\/")
                            [(namespace fq) (name fq)])
                nmsp (first split-var)
                n (second split-var)]
            (require (symbol nmsp))
            (resolve (symbol nmsp n)))
     ;; TODO sci
     :cljs (clojure.edn/read-string fq)))

(defn-spec pull-entry-by-key-name ::vt/map
  "Filters a map returning the entry matching the key-name"
  [key-name ::vt/str m ::vt/map]
  (medley.core/filter-keys #(= key-name (name %)) m))

(defn-spec id-kw-for ::vt/qkw-or-nil
  "Catch all function that produces an idkw for a variety of types."
  [thing ::vt/any]
  (cond
    (map? thing) (if-let [id-only-map (pull-entry-by-key-name "id" thing)]
                   (ffirst id-only-map)
                   nil)
    (vector? thing) (keyword (-> thing first namespace) "id")
    (qualified-keyword? thing) (keyword (namespace thing) "id")
    (keyword? thing) (keyword (name thing) "id")
    (string? thing) (keyword thing "id")
    :else nil))

(defn-spec id-of ::vt/kw-nil-or-str
  "Takes a map with a namespaced id key and returns the value for the key named 'id'."
  [m ::vt/map]
  (when (map? m) (first (vals (pull-entry-by-key-name "id" m)))))

(defn-spec plural-datatype-name-of ::vt/str [thing ::vt/any]
  (cond
    (map? thing) (-> (id-kw-for thing) namespace inflection/plural)
    (qualified-keyword? thing) (-> thing namespace inflection/plural)
    (keyword? thing) (-> thing name inflection/plural)
    (vector? thing) (-> thing first namespace inflection/plural)
    :else nil))

#?(:clj (defn safe-kws
          ([thing]
           (let [s (cond
                     (map? thing) (str (plural-datatype-name-of thing) "/" (safe-kws (id-of thing)))
                     (qualified-keyword? thing) (str (namespace thing) "/" (name thing))
                     (keyword? thing) (name thing)
                     (pred/entity-ident? thing) (str (plural-datatype-name-of thing) "/" (safe-kws (second thing)))
                     (vector? thing) (clojure.string/join "_xx_" (mapv safe-kws thing))
                     (string? thing) thing)]
             (-> s
                 (clojure.string/replace #"\/" "_ns_")
                 (clojure.string/replace #"\?" "_qm_")
                 (clojure.string/replace #"\$" "_dollar_")
                 (clojure.string/replace #"\*" "_star_")
                 (clojure.string/replace #"\!" "_bang_")
                 (clojure.string/replace #"\." "_dot_")
                 (clojure.string/replace #"\<" "_lt_")
                 (clojure.string/replace #"\>" "_gt_"))))
          ([underscore-replacement thing] (clojure.string/replace (safe-kws thing) #"\_" underscore-replacement)))
   :cljs (defn safe-kws [thing] thing))

(defn-spec safe-string->kw ::vt/qkw-or-vec-of-qkws
  "safe storage string -> useful kw"
  [n ::vt/kw-or-str]
  (if (keyword? n)
    n
    (if (clojure.string/includes? n "_xx_")
      (mapv safe-string->kw (clojure.string/split n #"_xx_"))
      (let [n0 (clojure.string/replace n #"_ndda_" "-")
            n1 (clojure.string/replace n0 #"_qm_" "?")
            n2 (clojure.string/replace n1 #"_dollar_" "$")
            n3 (clojure.string/replace n2 #"_star_" "*")
            n3 (clojure.string/replace n2 #"_bang_" "!")
            n4 (clojure.string/replace n3 #"_dot_" ".")
            n5 (clojure.string/replace n3 #"_lt_" "<")
            n6 (clojure.string/replace n3 #"_gt_" ">")
            n7 (clojure.string/split n4 #"_ns_")]
        (apply keyword n7)))))

(defn-spec ensure-safe-string ::vt/str [kw-or-str ::vt/kw-or-str]
  (if (string? kw-or-str)
    kw-or-str
    (safe-kws kw-or-str)))


(defn-spec m->pairs ::vt/vec
  [m ::vt/map]
  (vec (flatten (into '[] m))))


(defn-spec merge-with-existing-key ::vt/map [m ::vt/map, k ::vt/kw, m-to-merge ::vt/map]
  (assoc m k (merge (k m)
                     m-to-merge)))


(defn-spec perform-inverted ::vt/map
  [m ::vt/map f ::vt/map]
  (->> m clojure.set/map-invert f clojure.set/map-invert))

(defn-spec filter-keys-by-ns ::vt/any
  [m ::vt/map nmsp ::vt/kw-or-str]
  (if (string? m)
    (medley.core/filter-keys #(= ns-k-or-ns-str (namespace %)) m)
    (medley.core/filter-keys #(= ns-k-or-ns-str (keyword (namespace %))) m)))

(defn-spec only-keys-in-ns ::vt/seq
  [nmsp ::vt/kw-or-str m ::vt/map]
  (medley.core/filter-keys #(= (name nmsp) (namespace %)) m))

(defn-spec remove-keys-with-nil-vals ::vt/seq
  [m ::vt/map]
  (medley.core/filter-vals #(not= nil %) m))

(defn-spec filter-where ::vt/seq
  [where-clause ::vt/vec sek ::vt/seq]
  (->> sek (filter (where where-clause))))

(defn-spec filter-keys-where ::vt/seq [where-clause ::vt/vec sek ::vt/seq ]
  (->> sek (medley.core/filter-keys (where where-clause))))

(defn-spec filter-vals-where ::vt/seq [where-clause ::vt/vec sek ::vt/seq ]
  (->> sek (medley.core/filter-vals (where where-clause))))

(defn-spec remove-where ::vt/seq
  [where-clause ::vt/vec sek ::vt/seq ]
  (->> sek (remove (where where-clause))))

(defn-spec remove-keys-where ::vt/seq [where-clause ::vt/vec sek ::vt/seq ]
  (->> sek (medley.core/remove-keys (where where-clause))))

(defn-spec remove-vals-where ::vt/seq [where-clause ::vt/vec sek ::vt/seq ]
  (->> sek (medley.core/remove-vals (where where-clause))))

(defn-spec remove-when-key-is-nil ::vt/seq
  [k ::vt/kq sek ::vt/seq ]
  (remove-where [k = nil] sek))

(defn-spec remove-keys ::vt/seq
  [ks ::vt/all-kws sek ::vt/seq]
  (->> sek (map (fn [m]
                  (let [mks (set (keys m))
                        selection  (clojure.set/difference mks ks)]
                    (select-keys m selection))))))



;; END VALUETYPE FNS

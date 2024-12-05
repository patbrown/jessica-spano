(ns baby.pat.vt
  (:require [baby.pat.vt.inflections :as inflections]
            [baby.pat.vt.no-rando :refer [no-rando]]
            [clojure.edn]
            [clojure.spec.alpha :as s]
            [clojure.string]
            [clojure.walk]
            [exoscale.coax :as c]
            [medley.core]
            [orchestra.core #?(:clj :refer :cljs :refer-macros) [defn-spec]]
            [pyramid.core]
            [tick.core]))

;; ### INFLECTIONS
(def plural inflections/plural)
(def singular inflections/singular)
(def plural-keyword inflections/plural-keyword)
(def singular-keyword inflections/singular-keyword)

;; end inflections

;; ### BASE SPECS
(s/def ::? boolean?)
(s/def ::any any?)
(s/def ::bool ::?)
(s/def ::coll coll?)
(s/def ::discard ::any)
(s/def ::double double?)
(s/def ::false false?)
(s/def ::fn fn?)
(s/def ::inst inst?)
(s/def ::int integer?)
(s/def ::kw keyword?)
(s/def ::long ::int)
(s/def ::map map?)
(s/def ::nil nil?)
(s/def ::num (s/or ::long ::long ::double ::double))
(s/def ::qkw (s/and ::kw qualified-keyword?))
(s/def ::seq seq?)
(s/def ::set set?)
(s/def ::str string?)
(s/def ::string ::str)
(s/def ::true true?)
(s/def ::vec vector?)
(s/def :db.type/boolean boolean?)
(s/def :db.type/double double?)
(s/def :db.type/instant inst?)
(s/def :db.type/keyword keyword?)
(s/def :db.type/long integer?)
(s/def :db.type/string string?)

;; END BASE SPECS

;; ### OPTIONALITY SPECS
(s/def ::?-or-nil (s/or ::? ::? ::nil ::nil))
(s/def ::atom-kw-or-str (s/or ::atom ::atom ::kw ::kw ::str ::str))
(s/def ::atom-kw-str-or-vec (s/or ::atom ::atom ::kw ::kw ::str ::str ::vec ::vec))
(s/def ::atom-kw-map-or-str (s/or ::atom ::atom ::kw ::kw ::map ::map ::str ::str))
(s/def ::atom-map-or-vec (s/or ::atom ::atom ::map ::map ::vec ::vec))
(s/def ::atom-or-fn (s/or ::atom ::atom ::fn ::fn))
(s/def ::atom-or-map (s/or ::atom ::atom ::map ::map))
(s/def ::atom-or-vec (s/or ::atom ::atom ::vec ::vec))
(s/def ::coll-or-nil (s/or ::coll ::coll ::nil ::nil))
(s/def ::coll-or-str (s/or ::coll ::coll ::str ::str))
(s/def ::double-or-nil (s/or ::double ::double ::nil ::nil))
(s/def ::fn-ident-or-map (s/or ::fn ::fn ::ident ::ident ::map ::map))
(s/def ::fn-kw-or-str (s/or ::fn ::fn ::kw ::kw ::str ::str))
(s/def ::fn-or-ident (s/or ::fn ::fn ::ident ::ident))
(s/def ::fn-or-kw (s/or ::fn ::fn ::kw ::kw))
(s/def ::fn-or-nil (s/or ::fn ::fn ::nil ::nil))
(s/def ::fn-or-str (s/or ::fn ::fn ::str ::str))
(s/def ::fn-or-vec (s/or ::fn ::fn ::vec ::vec))
(s/def ::fn-qkw-or-str (s/or ::qkw ::qkw ::str ::str ::fn ::fn))
(s/def ::kw-map-or-vec (s/or ::vec ::vec ::map ::map ::kw ::kw))
(s/def ::kw-map-or-str (s/or ::str ::str ::map ::map ::kw ::kw))
(s/def ::kw-map-set-or-vec (s/or ::kw ::kw ::map ::map ::set ::set ::vec ::vec))
(s/def ::kw-map-str-or-vec (s/or ::kw ::kw ::map ::map ::str ::str ::vec ::vec))
(s/def ::kw-nil-or-str (s/or ::kw ::kw ::nil ::nil ::str ::str))
(s/def ::kw-or-map (s/or ::kw ::kw ::map ::map))
(s/def ::kw-or-nil (s/or ::kw ::kw ::nil ::nil))
(s/def ::kw-or-str (s/or ::kw ::kw ::str ::str))
(s/def ::kw-set-or-vec (s/or ::kw ::kw ::set ::set ::vec ::vec))
(s/def ::kw-str-or-vec (s/or ::kw ::kw ::str ::str ::vec ::vec))
(s/def ::long-or-nil (s/or ::long ::long ::nil ::nil))
(s/def ::map-or-nil (s/or ::map ::map ::nil ::nil))
(s/def ::map-or-str (s/or ::map ::map ::str ::str))
(s/def ::map-or-vec (s/or ::vec ::vec ::map ::map))
(s/def ::num-or-nil (s/or ::num ::num ::nil ::nil))
(s/def ::qkw-or-nil (s/or ::qkw ::qkw ::nil ::nil))
(s/def ::qkw-or-str (s/or ::qkw ::qkw ::str ::str))
(s/def ::qkw-or-vec-of-qkws (s/or ::qkw ::qkw ::vec-of-qkws ::vec-of-qkws))
(s/def ::qkw-str-or-vec (s/or ::qkw ::qkw ::str ::str ::vec ::vec))
(s/def ::seq-or-nil (s/or ::seq ::seq ::nil ::nil))
(s/def ::set-or-nil (s/or ::set ::set ::nil ::nil))
(s/def ::set-or-vec (s/or ::set ::set ::vec ::vec))
(s/def ::str-or-nil (s/or ::str ::str ::nil ::nil))
(s/def ::vec-or-nil (s/or ::vec ::vec ::nil ::nil))

;; END OPTIONALITY SPECS

;; ### HOMOGENOUS SPECS
(s/def ::all-colls (s/and ::coll #(every? coll? %)))
(s/def ::all-doubles (s/and ::coll #(every? double? %)))
(s/def ::all-fns (s/and ::coll #(every? fn? %)))
(s/def ::all-kws (s/and ::coll #(every? keyword? %)))
(s/def ::all-longs (s/and ::coll #(every? integer? %)))
(s/def ::all-maps (s/and ::coll #(every? map? %)))
(s/def ::all-nils (s/and ::coll #(every? nil? %)))
(s/def ::all-nums (s/and ::coll #(every? number? %)))
(s/def ::all-qkws (s/and ::coll #(every? qualified-keyword? %)))
(s/def ::all-seqs (s/and ::coll #(every? seq? %)))
(s/def ::all-sets (s/and ::coll #(every? set? %)))
(s/def ::all-strs (s/and ::coll #(every? string? %)))
(s/def ::all-vecs (s/and ::coll #(every? vector? %)))
(s/def ::set-of-kws (s/and ::set ::all-kws))
(s/def ::set-of-qkws (s/and ::set ::all-qkws))
(s/def ::set-of-strs (s/and ::set ::all-strs))
(s/def ::vec-of-kws (s/and ::vec ::all-kws))
(s/def ::vec-of-maps (s/and ::vec ::all-maps))
(s/def ::vec-of-qkws (s/and ::vec ::all-qkws))
(s/def ::vec-of-strs (s/and ::vec ::all-strs))
(s/def ::vec-of-vecs (s/and ::vec ::all-vecs))

;; END HOMOGENOUS SPECS

;; ### Extended Specs Helper Fns

(defn-spec unqualified-keyword? ::?
  "Is a thing a keyword with no namespace."
  [thing ::any]
  (and (keyword? thing) (not (qualified-keyword? thing))))

(defn-spec type-of? ::?
  "Does the type of a thing as a string include the string provided?"
  [thing ::any string ::str]
  (clojure.string/includes? (str (type thing)) string))

(defn-spec atom? ::?
  "Is a thing an atom?"
  [thing ::any] 
  #?(:clj (type-of? thing "clojure.lang.Atom")
     :cljs (instance? cljs.core/Atom thing)))

(defn-spec all-valid-examples-of? ::? [things ::any vt ::qkw]
  (every? (fn [x] (s/valid? vt x)) things))

(defn-spec contains-keys? ::?
  "Does a map contain all the provided keys?"
  ([m ::map k ::kw]
   (contains? (set (keys m)) k))
  ([m ::map k ::kw & ks ::coll-of-kws]
   (every? true? (map #(contains-keys? m %) (flatten [k ks])))))

(defn-spec map-with? ::?
  "Does a map contain all these keys?"
  [& qkws ::coll-of-kws]
  (s/and ::map #(apply contains-keys? (flatten [% qkws]))))

(defn-spec walk-dissoc ::map
  "Walks a map returning it without the provided keys."
  [m ::map k ::kw & ks ::coll-of-kws]
    (clojure.walk/prewalk
     (fn [v]
       (if (map? v)
         (apply dissoc v (flatten [k ks]))
         v)) m))

(defn-spec pull-entry-by-key-name ::map
  "Filters a map returning the entry matching the key-name"
  [key-name ::str m ::map]
  (medley.core/filter-keys #(= key-name (name %)) m))


(defn-spec entity-ident? ::?
  "Is a thing an entity id?"
  [thing ::any]
  (and (vector? thing)
       (= 2 (count thing))
       (keyword? (first thing))
       (= "id" (name (first thing)))))

(defn-spec entity-idents?  ::?
  "Is this thing a vector of entity ids?"
  [thing ::any]
  (and (vector? thing)
       (every? entity-ident? thing)))

(defn-spec entity-ident-or-idents? ::?
  "Is this thing an ident or group of them?"
  [thing ::any]
  (or (entity-ident? thing) (entity-idents? thing)))


(defn-spec id-kw-for-map ::qkw-or-nil
  "Takes a map that presumably has a key with the name id and returns it's namespace qualified id key."
  [m ::map]
  (when (map? m)
    (if-let [id-only-map (pull-entry-by-key-name "id" m)]
      (ffirst id-only-map)
      nil)))

(defn-spec id-kw-for-path ::qkw-or-nil
  "DO I NEED THIS? WHAT USE IS THIS? HOW'D THIS GET HERE? WHERE IS KONDO?"
  [path ::vec]
  (when (vector? path)
    (keyword (-> path first namespace) "id")))


(defn-spec id-kw-for ::qkw-or-nil
  "Catch all function that produces an idkw for a variety of types."
  [thing ::any]
  (cond
    (map? thing) (id-kw-for-map thing)
    (vector? thing) (id-kw-for-path thing)
    (qualified-keyword? thing) (keyword (namespace thing) "id")
    (keyword? thing) (keyword (name thing) "id")
    (string? thing) (keyword thing "id")
    :else nil))

(defn-spec id-of ::kw-nil-or-str
  "Takes a map with a namespaced id key and returns the value for the key named 'id'."
  [m ::map]
  (when (map? m) (first (vals (pull-entry-by-key-name "id" m)))))

(defn-spec plural-datatype-name-of ::str [thing ::any]
  (cond
    (map? thing) (-> (id-kw-for thing) namespace plural)
    (qualified-keyword? thing) (-> thing namespace plural)
    (keyword? thing) (plural (name thing))
    (vector? thing) (-> thing first namespace plural)
    :else nil))

#?(:clj (defn safe-kws
          ([thing]
           (let [s (cond
                     (map? thing) (str (plural-datatype-name-of thing) "/" (safe-kws (id-of thing)))
                     (qualified-keyword? thing) (str (namespace thing) "/" (name thing))
                     (keyword? thing) (name thing)
                     (entity-ident? thing) (str (plural-datatype-name-of thing) "/" (safe-kws (second thing)))
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

(defn-spec safe-string->kw ::qkw-or-vec-of-qkws
  "safe storage string -> useful kw"
  [n ::kw-or-str]
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

(defn-spec ensure-safe-string ::str [kw-or-str ::kw-or-str]
  (if (string? kw-or-str)
    kw-or-str
    (safe-kws kw-or-str)))

(defn-spec id-qkw? ::?
  "Is a thing a qualified id keyword?"
  [thing ::kw]
  (= "id" (name thing)));; END HELPERS

;; ### Extended Specs
(s/def ::ukw (s/and ::kw unqualified-keyword?))
(s/def ::ukw-or-nil (s/or ::ukw ::ukw ::nil ::nil))
(s/def ::all-ukws (s/and ::coll #(every? unqualified-keyword? %)))
(s/def ::atom #(atom? %))
(s/def ::atom-or-nil (s/or ::atom ::atom ::nil ::nil))
(s/def ::all-atoms (s/and ::coll #(every? atom? %)))
(s/def ::idkw (s/and ::qkw #(id-qkw? %)))
(s/def ::has-2 #(= 2 (count %)))
(s/def ::ident (s/and ::vec ::has-2 #(id-qkw? (first %))))
(s/def ::idents (s/and ::coll #(all-valid-examples-of? % ::ident)))
(s/def ::ident-or-idents (s/or ::ident ::ident ::idents ::idents))
(s/def ::ident-or-qkw (s/or ::ident ::ident ::qkw ::qkw))
(s/def ::ident-or-map (s/or ::ident ::ident ::map ::map))
(s/def ::ident-or-var-path (s/or ::ident ::ident ::var-path ::var-path))
(s/def ::ident-kw-or-str (s/or ::ident ::ident ::kw ::kw ::str ::str))
(s/def ::ident-map-or-qkw (s/or ::ident ::ident ::qkw ::qkw ::map ::map))
(s/def ::ident-idents-or-nil (s/or ::ident ::ident ::idents ::idents ::nil ::nil))
(s/def ::has-64 (s/and ::coll (fn [thing] (= 64 (count thing)))))
(s/def ::qid (s/and ::str ::has-64 ::str-without-numbers))
(s/def ::vt (s/and ::map #(contains-keys? % ::id ::vt) #(clojure.string/includes? (namespace (::id %)) "vt")))
(s/def ::vt-id (s/and ::qkw #(clojure.string/includes? (namespace %) "vt")))
(s/def ::reentrant-lock #(type-of? % "java.util.concurrent.locks.ReentrantLock"))
(s/def ::core-async-channel #(type-of? % "clojure.core.async"))
(s/def ::bytes #?(:clj bytes? :cljs any?))
(s/def ::encrypted (map-with? :data :iv))
(s/def ::file #(type-of? % "java.io.File"))
(s/def ::vec-of-files (s/and ::vec (s/coll-of ::file)))
(s/def ::vec-of-2 (s/and ::vec ::has-2))

(defn-spec ident-for ::ident
  "Takes a map and returns the normalized ident for it based on the id."
  [m ::map]
  (first (vec (pull-entry-by-key-name "id" m))))
;; ## End Ident

;; ### TIME SPECS
(s/def ::instant #?(:clj #(type-of? % "java.time.Instant")
                    :cljs ::any))
(s/def ::time-pair (fn [x] (and (vector? x) (integer? (first x)) (keyword? (second x)))))
(s/def ::instant-or-long (s/or ::instant ::instant ::long ::long))
(s/def ::instant-or-num (s/or ::instant ::instant ::num ::num))

;; ### RANDOM THINGS
(defn-spec rand-str ::str
  "Creates a random string of `64` or supplied length upper and lower chars + numbers."
  ([] (rand-str 64))
  ([len ::num]
   (let [u (take len (repeatedly #(char (+ (rand 26) 65))))
         d (take len (repeatedly #(clojure.string/lower-case (char (+ (rand 26) 65)))))]
     (apply str (take len (shuffle (flatten [u d])))))))

(defn-spec qid ::str
  "Utility to create a simply id that's a unique string. Placeholder."
  []
  (rand-str 64))

(defn-spec rand-of ::str
  "Returns a random category member.   
   Can be provided category keyword ':nato-alphabet' "
  ([] (rand-of (rand-nth (vec (set (keys no-rando))))))
  ([k ::kw]
   (let [category (get no-rando k)]
     (when category
       (rand-nth (vec category))))))

(def rand-of-counts-map (medley.core/map-kv (fn [k v]
                                      [k (count v)]) no-rando))

;; END RANDOM


;; ### Valuetype Fns

(defn-spec vt-dispatch ::qkw
  "Takes a thing and returns it's value-type qualified keyword.
   This is a dumber case-based type dispatch."
  [thing ::any]
  (if (fn? thing)
    :vt/fn
    (cond
      (nil? thing) ::nil
      (vector? thing) ::vec
      (set? thing) ::set
      (map? thing) ::map
      (keyword? thing) ::kw
      (string? thing) ::str
      (integer? thing) ::long
      (double? thing) ::double
      (boolean? thing) ::?
      (type-of? thing "clojure.core.async") ::chan
      :else ::any)))

(defn-spec add-kw-ns ::any
  "Adds a provided namespace to the thing or things provided DWIM style."
  [new-namespace ::kw-or-str thing ::any]
  (let [rethink-this-shit-its-bogus-vt-any-are-you-kidding-me? (println (str "add-kw-ns takes keywords, maps, set, and vectors. You supplied: " thing ". Which is a " (type thing)))]
    (case (vt-dispatch thing)
      ::kw (keyword (name new-namespace) (name thing))
      ::map (medley.core/map-keys (comp #(keyword (name new-namespace) %) name) thing)
      ::set (into #{} (map #(add-kw-ns new-namespace %) thing))
      ::vec (mapv #(add-kw-ns new-namespace %) thing)
      ::any rethink-this-shit-its-bogus-vt-any-are-you-kidding-me?
      :else rethink-this-shit-its-bogus-vt-any-are-you-kidding-me?)))

(defn-spec rm-kw-ns ::any
  "Removes the namespace for a thing or things DWIM style."
  [thing ::any]
  (case (vt-dispatch thing)
    ::kw (keyword (name thing))
    ::map (medley.core/map-keys (comp keyword name) thing)
    ::set (set (map rm-kw-ns thing))
    ::vec (mapv rm-kw-ns thing)
    :else thing))


(defn-spec qkw->relative-path ::str
  [qkw ::qkw]
  (clojure.string/replace (str (namespace qkw) "/"
                               (name qkw)) #"\." "/"))

(defn-spec if-atom-deref ::any
  [thing ::any]
  (if (atom? thing)
    (deref thing)
    thing))


;; END VALUETYPE FNS

;; ### BASIS

(def now tick.core/now)
(defn-spec !instant! ::instant []
  "Returns now as an instant."
  (tick.core/now))

(defn-spec timestamp->instant ::instant
  "Takes the milliseconds off the time string of a `malformed` database time string and makes it an inst."
  [at ::str]
  (let [t-added     (-> at (clojure.string/replace #" " "T"))
        dropped-sub (-> t-added (clojure.string/split #"\.") first)
        inst-string (str dropped-sub "Z")]
    (tick.core/instant inst-string)))

(defn-spec instant->map ::map
  "Takes an instant of time and breaks it down into units."
  [t ::instant]
  (baby.pat.vt/add-kw-ns :time
                         {:yyyy (tick.core/int (tick.core/year t))
                          :MM   (tick.core/int (tick.core/month t))
                          :dd   (tick.core/day-of-month t)
                          :HH   (tick.core/hour t)
                          :mm   (tick.core/minute t)
                          :ss   (tick.core/second t)
                          :ms   (long (/ (tick.core/nanosecond t) 1000000))
                          :ns   (tick.core/nanosecond t)}))

(c/def ::instant->map (fn [x opts] (when (tick.core/instant? x) (instant->map x))))

(defn-spec map->instant ::instant
  "Takes a map of time and returns an instant."
  [{:time/keys [yyyy MM dd HH mm ss ms]} ::map]
  (let [frmt   (fn [VA] (if (= 1 (count (str VA))) (str "0" VA) VA))
        month  (frmt MM)
        day    (frmt dd)
        hour   (frmt HH)
        minute (frmt mm)
        second (frmt ss)]
    (tick.core/instant (str yyyy "-" month "-" day "T" hour ":" minute ":" second "." ms "Z"))))

(c/def ::map->instant (fn [x opts] (when (map? x) (map->instant x))))

(defn-spec time-pair->seconds ::long
  "Turns a pair of number/unit i.e. 12 :minutes and converts it to seconds."
  ([p ::time-pair]
   (let [years 31536000
         weeks 604800
         days  86400
         hours 3600]
     (* (first p) (case (second p)
                    :yy      years
                    :yyyy    years
                    :years   years
                    :weeks   weeks
                    :dd      days
                    :days    days
                    :hh      hours
                    :HH      hours
                    :hours   hours
                    :mm      60
                    :minutes 60
                    :ss      1
                    :seconds 1))))
  ([n ::long u ::kw] (time-pair->seconds [n u]))
  ([n ::long u ::kw & nus ::coll] (reduce + (map time-pair->seconds (partition 2 (flatten [n u nus]))))))

(defn-spec sum ::instant-or-num
  "Adds time by taking an inst, and number/unit pairs. i.e. 12 :minutes 43 :hours 4 :days, etc. Adds numbers via reduce."
  [original ::instant-or-num & others ::coll]
  (if (tick.core/instant? original)
    (let [s (reduce + (for [p (partition 2 others)]
                        (time-pair->seconds p)))]
      (tick.core/instant (tick.core/>> original
                                       (tick.core/new-duration s :seconds))))
    (reduce + original others)))

(defn-spec subtract ::instant-or-num
  "Subtracts time by taking an inst, and number/unit pairs. i.e. 12 :minutes 43 :hours 4 :days, etc. Subtracts numbers via reduce."
  [original ::instant-or-num & others ::coll]
  (if (tick.core/instant? original)
    (let [s (reduce + (for [p (partition 2 others)]
                        (time-pair->seconds p)))]
      (tick.core/instant (tick.core/<< original (tick.core/new-duration s :seconds))))
    (reduce - original others)))

(defn-spec before? ::?
  "Is the first thing before the second?"
  [established ::instant-or-num in-question ::instant-or-num]
  (if (tick.core/instant? established)
    (tick.core/< established in-question)
    (< established in-question)))

(defn-spec after? ::?
  "Is the first thing after the second?"
  [established ::instant-or-num in-question ::instant-or-num]
  (if (tick.core/instant? established)
    (tick.core/> established in-question)
    (> established in-question)))

(defn-spec at-or-before? ::?
  "Is the first thing at or before the second?"
  [established ::instant-or-num in-question ::instant-or-num]
  (if (tick.core/instant? established)
    (tick.core/<= established in-question)
    (<= established in-question)))

(defn-spec at-or-after? ::?
  "Is the first thing at or after the second?"
  [established ::instant-or-num in-question ::instant-or-num]
  (if (tick.core/instant? established)
    (tick.core/>= established in-question)
    (>= established in-question)))

(defn-spec not-before? ::?
  "Is the first thing not before the second?"
  [established ::instant-or-num in-question ::instant-or-num]
  (not (before? established in-question)))

(defn-spec not-after? ::?
  "Is the first thing not after the second?"
  [established ::instant-or-num in-question ::instant-or-num]
  (not (after? established in-question)))

(defn-spec between? ::?
  "Given the start and end is the thing in question inside both?"
  [basis-start ::instant-or-num basis-end ::instant-or-num in-question ::instant-or-num]
  (and (after? basis-start in-question)
       (before? basis-end in-question)))

(defn-spec in-bounds? ::?
  "Given the start and end is the thing in question within both?"
  [basis-start ::instant-or-num basis-end ::instant-or-num in-question ::instant-or-num]
  (and (at-or-before? basis-end in-question)
       (at-or-after? basis-start in-question)))

(defn-spec duration-in-seconds ::long
  "Gives the duration of a time range in seconds. Useful for qualifying drilling activities based on sparse info."
  [time-start ::instant time-end ::instant]
  (-> (tick.core/duration {:tick/beginning time-start
                           :tick/end       time-end})
      tick.core/seconds))

;; ### Normaliza Base

(defn-spec normalizable? ::?
  "Can the provided map be normalized?"
  [m ::map]
  (let [x (atom [])]
    (clojure.walk/postwalk #(when (and (qualified-keyword? %) (= "id" (name %)))
                              (swap! x conj :true)) m)
    (when-not (empty? @x) true)))

(defn-spec is-already-normalized? ::?
  "Is the provided map already normalized?"
  [m ::map]
  (if-not (normalizable? m)
    false
    (let [all-keys-are-id-keys (every? #(= "id" %) (map name (keys m)))
          all-vals-are-maps (every? map? (vals m))
          monster-entity-predicate
          (when (every? true? (map map? (vals m)))
            (every? true? (vals (apply merge
                                       (map #(medley.core/map-kv
                                              (fn [k v]
                                                (let [id-kw (ffirst (medley.core/filter-keys (fn [k] (= "id" (name k))) v))]
                                                  [k (if (and (map? v)
                                                              (contains? (set (map name (keys v))) "id")
                                                              (= k (get v id-kw)))
                                                       true
                                                       (set (keys v)))])) %) (vals m))))))]
      (every? true? [monster-entity-predicate all-keys-are-id-keys all-vals-are-maps]))))

(defn-spec follow-path ::vec
  "Follows normalized entities to return the true value coords of path. Ripped from fulcro.a.n-s"
  [m ::map path ::vec]
  (loop [[h & t] path
         new-path []]
    (if h
      (let [np (conj new-path h)
            c  (get-in m np)]
        (if (s/valid? ::ident c)
          (recur t c)
          (recur t (conj new-path h))))
      (if (not= path new-path)
        new-path
        path))))

(defn-spec wrecklessly-handle-possible-atoms ::vec
  "A helpful function to help with atoms.
   Returns [is-atom? safe-thing]"
  [thing ::any]
  (if (instance? #?(:clj clojure.lang.IAtom
                    :cljs cljs.core/Atom) thing)
    [true @thing]
    [false thing]))

(defn-spec wrecklessly-dispatch-new-map ::discard
  "A wrapper that helps make consistent return values for atoms and maps"
  [is-atom? ::? m ::any new-m ::any]
  (if is-atom?
    (reset! m new-m)
    new-m))
;; END NORMALIZA BASE

;; ## EMPLOY

(defn-spec wrecklessly-employ ::vec-of-2
  "Employ function that returns the value of a path in the map"
  [m ::atom-or-map path ::atom-kw-str-or-vec]
  (let [[_ safe-m] (wrecklessly-handle-possible-atoms m)
        [_ safe-path] (wrecklessly-handle-possible-atoms path)]
    [safe-m safe-path]))

(defmulti *employ (fn [variant & args] variant))
(defmethod *employ :default [_ m path]
  (let [[safe-m safe-path] (wrecklessly-employ m path)]
    (get-in safe-m (follow-path safe-m safe-path))))

(defn-spec return-as-instance ::map
  "If given a path, return the map entry, ortherwise return the map. A variant can be provided first to use a named method of employ."
  ([m ::map thing ::map-or-vec] (return-as-instance :default m thing))
  ([variant ::kw m ::map thing ::map-or-vec]
   (if (map? thing)
     thing
     (*employ variant m thing))))

(defn-spec return-as-function ::fn
  "Returns  either the function given or the function stored at the path."
  ([m ::map thing ::fn-or-vec] (return-as-function :default m thing))
  ([variant ::kw m ::map thing ::fn-or-vec]
   (if (fn? thing)
     thing
     (:function/function (*employ variant m thing)))))

(defmethod *employ :follow [_ m path]
  (let [[safe-m safe-path] (wrecklessly-employ m path)]
    (clojure.walk/postwalk #(cond
                              (s/valid? ::ident %) (get-in safe-m %)
                              (s/valid? ::idents %) (map (get-in safe-m %))
                              :else %)
                           (*employ :default safe-m safe-path))))


(defmethod *employ :overlay [_ m overlay underlay]
  (let [overlay (return-as-instance m overlay)
        underlay (return-as-instance m underlay)]
    (merge underlay overlay)))

(defmethod *employ :underlay [_ m underlay overlay]
  (*employ :overlay underlay overlay))

(defmethod *employ :follow-overlay [_ m overlay underlay]
  (let [overlay (return-as-instance :follow m overlay)
        underlay (return-as-instance :follow m underlay)]
    (merge underlay overlay)))

(defmethod *employ :follow-underlay [_ m underlay overlay]
  (*employ :follow-overlay underlay overlay))

(defmethod *employ :follow-clone [_ m subject xform]
  (let [subject (return-as-instance :follow m subject)
        xform (return-as-function :follow m xform)])
  (xform subject))

(defmethod *employ :clone [_ m subject xform]
  (let [subject (return-as-instance m subject)
        xform (return-as-function m xform)])
  (xform subject))

(defmethod *employ :keys [_ m path]
  (let [result (*employ :default m path)]
    (keys result)))

(defmethod *employ :follow-keys [_ m path]
  (let [result (*employ :follow m path)]
    (keys result)))

(defmethod *employ :count [_ m path]
  (let [result (*employ :default m path)]
    (count result)))

(defmethod *employ :follow-count [_ m path]
  (let [result (*employ :follow m path)]
    (count result)))

(defn-spec employ ::any
  ([m ::atom-or-map path ::atom-kw-str-or-vec]
   (apply *employ [:default m path]))
  ([variant ::kw m ::atom-or-map path ::atom-kw-str-or-vec]
   (apply *employ [variant m path]))
  ([variant ::kw m ::atom-or-map path ::atom-kw-str-or-vec & args ::any]
   (apply *employ (into [variant m path] (vec args)))))

(defn-spec <- ::any [m ::atom-or-map path ::vec] (employ :default m path))

(defn-spec <-overlay ::map
  [m ::atom-or-map overlay ::map-or-vec underlay ::map-or-vec]
  (employ :overlay m overlay underlay))

(defn-spec <-underlay ::map
  [m ::atom-or-map underlay ::map-or-vec overlay ::map-or-vec]
  (employ :underlay m underlay overlay))

(defn-spec <-keys ::set
  [m ::atom-or-map path ::vec]
  (employ :keys m path))

(defn-spec <-count ::long
  [m ::atom-or-map path ::vec]
  (employ :count m path))

(defn-spec <-clone ::any
  [m ::atom-or-map subject ::map-or-vec xform ::fn-or-vec]
  (employ :clone m subject xform))

(defn-spec <<- ::any
  [m ::atom-or-map path ::vec]
  (employ :follow m path))

(defn-spec <<-overlay ::map
  [m ::atom-or-map path ::vec underlay ::map-or-vec]
  (employ :follow-overlay m path underlay))

(defn-spec <<-underlay ::map
  [m ::atom-or-map path ::vec overlay ::map-or-vec]
  (employ :follow-underlay m path overlay))

(defn-spec <<-keys ::set
  [m ::atom-or-map path ::vec]
  (employ :follow-keys m path))

(defn-spec <<-clone ::any
  [m ::atom-or-map subject ::map-or-vec xform ::fn-or-vec]
  (employ :follow-clone m subject xform))

(defn-spec <<-count ::long
  [m ::atom-or-map path ::vec]
  (employ :follow-count m path))

(comment
  (*employ :overlay (atom {:a/id {"sex" {:a/id "sex" :a/a "nuts"}}})
           [:a/id "sex"]
           {:a/a "lips" :a/b 999})
  {:a/a "nuts", :a/b 999, :a/id "sex"}
  (*employ :underlay (atom {:a/id {"sex" {:a/id "sex" :a/a "nuts"}}})
           [:a/id "sex"]
           {:a/a "lips" :a/b 999})
  {:a/id "sex", :a/a "lips", :a/b 999}
)
;; END EMPLOY

;; ## RM
(defn-spec remove-normalized ::map
  "Removes a path from a normalized map."
  [m ::map path ::vec]
  (pyramid.core/delete m path))

(defmulti *rm (fn [variant & args] variant))

(defn-spec follow-remove-normalized ::map
  "Removes a path and all it's references from a normalized map."
  [m ::map path ::vec]
  (let [a (atom [path])
        ev (vec (filter vector? (vals (get-in m path))))
        _ (doall (map #(when (s/valid? ::ident-or-idents %)
                        (swap! a conj %)) ev))
        for-removal (mapv vec (partition 2 (flatten @a)))]
    (loop [xs (seq for-removal)
           result m]
      (if xs
        (let [x (first xs)]
          (recur (next xs) (*rm :default result x)))
        result))))

(defmethod *rm :default [_ m path]
  (let [[is-atom? m] (wrecklessly-handle-possible-atoms m)
        [_ safe-path] (wrecklessly-handle-possible-atoms path)
        new-m (remove-normalized m safe-path)]
    (wrecklessly-dispatch-new-map is-atom? m new-m)))

(defmethod *rm :follow [_ m path]
  (let [[is-atom? m] (wrecklessly-handle-possible-atoms m)
        [_ safe-path] (wrecklessly-handle-possible-atoms path)
        new-m (follow-remove-normalized m safe-path)]
    (wrecklessly-dispatch-new-map is-atom? m new-m)))

(defn-spec rm ::atom-or-map
  "Removes an ident from m."
  ([m ::atom-or-map path ::atom-or-vec]
   (*rm :default m path))
  ([variant ::kw m ::atom-or-map path ::atom-kw-str-or-vec]
   (*rm variant m path)))

;; END RM

;; ## CHANGE

(defn-spec default-change-wrapper ::atom-or-map
  ([variant ::kw map ::atom-or-map path ::vec function ::fn]
   (let [[is-atom? safe-map] (wrecklessly-handle-possible-atoms map)
         [_ safe-path] (wrecklessly-handle-possible-atoms path)
         [_ safe-function] (wrecklessly-handle-possible-atoms function)
         new-map (update-in safe-map (follow-path safe-map safe-path) safe-function)]
     (wrecklessly-dispatch-new-map is-atom? map new-map)))
  
  ([variant ::kw map ::atom-or-map path ::vec function ::fn args ::any]
   (let [[is-atom? safe-map] (wrecklessly-handle-possible-atoms map)
         [_ safe-path] (wrecklessly-handle-possible-atoms path)
         [_ safe-function] (wrecklessly-handle-possible-atoms function)
         [_ safe-args] (wrecklessly-handle-possible-atoms args)
         new-map (update-in safe-map (follow-path safe-map safe-path) safe-function safe-args)]
     (wrecklessly-dispatch-new-map is-atom? map new-map))))

(defmulti *change (fn [variant & args] variant))
(defmethod *change :default
  ([variant m path function]
   (apply default-change-wrapper [variant m path function]))
  ([variant m path function args]
   (apply default-change-wrapper [variant m path function args])))

(defn-spec change ::atom-or-map
  "Takes m, path, and then the args to '(swap! c update-in p ...)' or '(update-in c p ...)'."
  ([m ::atom-or-map path ::atom-kw-str-or-vec function ::atom-or-fn]
   (*change :default m path function))
  ([m ::atom-or-map path ::atom-kw-str-or-vec function ::atom-or-fn args ::any]
   (*change :default m path function args)))

;; END CHANGE

;; ## ADD

(defn-spec entity-as-instance ::map
  "Turn an entity into an instance."
  [entity ::map]
  (let [iid (qid)
        id (ffirst (medley.core/filter-keys #(= "id" (name %)) entity))]
    {:instance/id (keyword "instance" iid)
     :instance/dt [:dt/id (keyword "dt" (-> id namespace))]
     :instance/instance (assoc entity :instance/ident [:instance/id iid])}))

(defmulti *normalize-as (fn [variant & things] variant))
(defmethod *normalize-as :default [_ m items]
  (pyramid.core/add m items))
(defmethod *normalize-as :as-instances [_ m items]
  (pyramid.core/add m (entity-as-instance items)))
(defmethod *normalize-as :add-all-vts [_ m]
  (let [vts (let [vts (set (filter #(= "vt" (namespace %)) (keys (s/registry))))]
              (apply merge (map (fn [s] {s {::id s
                                            ::vt (s/form s)}}) vts)))]
    (assoc m ::id vts)))

(defn-spec add-normalized ::map
  ([safe-m ::map items ::map-or-vec] (add-normalized :default safe-m items))
  ([variant ::kw m ::atom-or-map items ::map-or-vec]
   (let [[is-atom? safe-m] (wrecklessly-handle-possible-atoms m)
         [_ safe-items] (wrecklessly-handle-possible-atoms items)]
     (if (vector? safe-items)
       (apply medley.core/deep-merge (map #(add-normalized variant safe-m %) safe-items))
       (if (and (normalizable? safe-items) (not (is-already-normalized? safe-items)))
         (*normalize-as variant safe-m safe-items)
         (medley.core/deep-merge safe-m safe-items))))))

(defn-spec add-normalized-boilerplate ::discard
  [variant ::kw m ::atom-or-map items ::map-or-vec]
  (let [[is-atom? safe-m] (wrecklessly-handle-possible-atoms m)
        [_ safe-items] (wrecklessly-handle-possible-atoms items)
        new-m (add-normalized variant safe-m safe-items)]
    (wrecklessly-dispatch-new-map is-atom? m new-m)))

(defmulti *add (fn [variant & things] variant))
(defmethod *add :default
  [variant m items] (add-normalized-boilerplate variant m items))
(defmethod *add :as-instances
  [variant m items] (add-normalized-boilerplate variant m items))
(defmethod *add :overlay
  [variant m items] (add-normalized-boilerplate variant m items))
(defmethod *add :add-all-vts
  [variant m] (add-normalized-boilerplate variant m nil))

(defn-spec add ::atom-or-map
  "Adds items to m as normalized collections."
  ([m ::atom-or-map items ::atom-map-or-vec]
   (*add :default m items))
  ([variant ::kw m ::atom-or-map items ::atom-map-or-vec]
   (*add variant m items)))

;; END ADD

;; ## ACCESS 

(defn-spec callable-fn-from-thing ::fn
  "Given a keyword or a string returns a callable function that thing resolves to."
  [fq ::qkw-or-str]
  #?(:clj (let [split-var (if (string? fq)
                            (clojure.string/split fq #"\/")
                            [(namespace fq) (name fq)])
                nmsp (first split-var)
                n (second split-var)]
            (require (symbol nmsp))
            (resolve (symbol nmsp n)))
     :cljs (clojure.edn/read-string fq)))

(defn-spec ident->default-value ::any
  "Returns the value stored in the default position for an ident.   
  i.e. [:a/id :a/cool] returns value at [:a/id :a/cool :a/a]"
  [m ::atom-or-map ident ::ident]
  (let [nmsp (-> ident first namespace)
        thing (<- m (into ident [(keyword nmsp nmsp)]))]
    thing))

(defn-spec id->default-value ::any
  "Returns the value stored in the default position for an id.
   i.e. :a/cool returns the value at [:a/id :a/cool :a/a]"
  [m ::atom-or-map id ::qkw]
  (let [id-kw (keyword (namespace id) "id")]
    (ident->default-value m [id-kw id])))

(defn-spec call-fn-from-ident ::fn-or-kw
  "Calls a function located at the ident."
  [m ::atom-or-map ident ::ident]
  (let [f (ident->default-value m ident)]
    (f)))

(defn-spec access ::any
  "Access an atom or map via an instructions map."
  [m ::atom-or-map {:keys [action supatom path function args] :as instructions} ::map]
  (let [action (ident->default-value m action)
        supatom (ident->default-value m supatom)
        _ (tap> {:a action :s supatom :path path :function function :args args})]
    (if (nil? path)
      (action supatom)
      (if (nil? function)
        (action supatom path)
        (if (nil? args)
          (action supatom path function)
          (action supatom path function args))))))

(defn-spec bang ::any
  "Provides a vector interface to access."
  ([v ::vec]
   (if (every? vector? v)
     (doall (map bang v))
     (let [m (first v)
           action (second v)
           supatom (when (> (count v) 2) (nth v 2))
           path (when (> (count v) 3) (nth v 3))
           function (when (> (count v) 4) (nth v 4))
           args (when (> (count v) 5) (nth v 5))
           access-map (medley.core/filter-vals
                       (fn [v] (when (not= nil v) v))
                       {:action action :supatom supatom :path path :function function :args args})]
       (access m access-map))))
  ([m ::atom-or-map v ::vec] (if (every? vector? v)
                                  (bang (mapv (fn [a-vec] (into [m] a-vec)) v))
                                  (bang (into [m] v)))))

(def ! bang)

(defn-spec access-this-with ::fn
  "Takes partial instructions and returns a primed function like `access` "
  [m ::atom-or-map partial-instructions ::vec]
  (fn [& v]
    (let [instructions (if (vector? (first v))
                         (into (into partial-instructions (first v)) (rest v))
                         (into partial-instructions v))]
      (! (into [m] instructions)))))

(defn-spec access-anything-with ::fn
  "Takes partial instructions and returns a function that needs a map and further instructions."
  [partial-instructions ::vec]
  (fn [m & v]
    (let [instructions (if (vector? (first v))
                         (into (into partial-instructions (first v)) (rest v))
                         (into partial-instructions v))]
      (! (into [m] instructions)))))

(comment

  (def u {:action/id {:change/default {:action/id :change/default
                                     :action/action (fn
                                                      ([supatom path function]
                                                       #_{:s supatom :p path :f function}
                                                       (change supatom path function))
                                                      ([supatom path function args]
                                                       #_{:s supatom :p path :f function :a args}
                                                       (change supatom path function args)))}}
        :supatom/id {:local/love {:supatom/id :local/love
                                  :supatom/supatom (atom {:a {:b 0}})}}})

  (def change-local-love (access-this-when-ready u [:change/default :local/love]))
         (change-local-love [[:a :b] - 9])

         (def cll2 (access-anything-when-ready [:change/default :local/love]))
         (cll2 u [[:a :b] inc])
         
;
         )

;; END ACCESS


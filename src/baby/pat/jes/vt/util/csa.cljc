(ns baby.pat.jes.vt.util.csa
  (:require [baby.pat.jes.vt :as vt]
            [clojure.spec.alpha :as s]
            [orchestra.core #?(:clj :refer :cljs :refer-macros) [defn-spec]]))

(defn-spec search-specs ::vt/any
  "Pulls the exact matching spec out of the registry. If no match is present. Finds specs that are similarly named in the registry"
  ([] (s/registry))
  ([partial-match-candidate ::vt/kw-or-str]
   (let [pmc (if (string? partial-match-candidate) (keyword partial-match-candidate) partial-match-candidate)
         fmt #(if (nil? (namespace %))
                (name %)
                (str (namespace %) "/" (name %)))
         filt (fmt pmc)
         exact-match (keys (into {} (filter #(-> % first (= pmc)) (s/registry))))]
     (if (not-empty exact-match)
       {pmc (s/form (first exact-match))}
       (merge (into {} (for [id (keys (into {} (filter #(-> % first fmt (clojure.string/includes? filt)) (s/registry))))]
                         {id (s/form id)}))))))
  ([partial-match-candidate ::vt/kw-or-str opt ::vt/kw]
   "Currently any option keyword will return a map with the spec as the key and the body as the val."
   (let [results (registry-search partial-match-candidate)
         fns (medley.core/filter-keys symbol? results)
         specs (medley.core/filter-keys keyword? results)
         ns-specs (filter #(= (name partial-match-candidate) (namespace %)) (keys specs))]
     (case opt
       :all results
       :fns fns
       :specs specs
       :specs-in-ns ns-specs
       :name (if (= 1 (count (keys results)))
               (first (keys results))
               (set (keys results)))
       :exists? (if (= 1 (count (keys results)))
                  true
                  false)))))

(defn-spec spec-map ::vt/map
  ([s ::vt/qkw-set-or-vec]
   (let [desc (s/describe s)]
     (into {} (map #(apply (fn [k v] {k v}) %)
                   (if (symbol? desc)
                     [[s desc]]
                     (map vec (partition 2 (rest desc)))))))))





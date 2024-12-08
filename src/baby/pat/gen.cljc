(ns baby.pat.gen
  (:refer-clojure :exclude [def])
  (:require [baby.pat.jes.vt :as vt]
            [clojure.spec.alpha :as s]
            [clojure.test.check.generators :as ctcg]
            [genman.core]
            [orchestra.core #?(:clj :refer :cljs :refer-macros) [defn-spec]]))

(defmacro def
  ([vt g]
   `(do (genman.core/defgenerator ~vt (clojure.spec.alpha/gen (clojure.spec.alpha/and ~vt ~g)))))
  ([vt ctx g]
   `(do (genman.core/with-gen-group ~ctx
          (genman.core/defgenerator ~vt (clojure.spec.alpha/gen (clojure.spec.alpha/and ~vt ~g)))))))

(defn-spec gen ::vt/any
  ([vtid ::vt/qkw]
   (ctcg/generate (genman.core/gen vtid)))
  ([vtid ::vt/qkw ctx ::vt/qkw]
   (if (keyword? vtid)
     (genman.core/with-gen-group ctx
       (ctcg/generate (genman.core/gen vtid)))
     (ctcg/generate (genman.core/use-gen-group ctx (genman.core/gen vtid))))))

(defn-spec sample ::vt/any
  ([id ::vt/qkw] (vec (ctcg/sample (genman.core/gen id))))
  ([id ::vt/qkw ctx ::vt/long-or-qkw] (if (number? ctx)
              (let [result (ctcg/sample (genman.core/gen id) ctx)]
                (if (= 1 ctx)
                  result
                  (vec result)))
              (vec (genman.core/use-gen-group ctx (ctcg/sample (genman.core/gen id))))))
  ([id ::vt/qkw ctx ::vt/qkw n ::vt/long] (vec (genman.core/use-gen-group ctx (ctcg/sample (genman.core/gen id) n)))))


(comment

(s/def ::asd integer?)
(s/def ::fj double?)
(s/def ::vt/long integer?)
(c/def ::vt/long->str (fn [x _] (str x)))
(s/def ::vt/str string?)
(c/def ::vt/str->long (fn [x _] (Long/valueOf x)))
(g/def ::vt/long ::vt/str (s/and int? #(read-string %)))
(g/def ::asd :home (s/and ::asd #(< % 10000) #(< % 50)))
(g/def ::asd :away #(and (< % 10000) (> % 50)))
(g/def ::fj :nice #(and (< % 10000) (> % 50)))

(g/sample ::asd :away 20)
(g/sample ::fj :nice 20)


  )

(ns baby.pat.vt.macros
  (:refer-clojure :exclude [slurp])
  (:require [medley.core]
            [camel-snake-kebab.core]
            #?@(:clj [[net.cgrand.macrovich :as macros]
                      [clojure.java.io]]))
  #?(:cljs (:require-macros [net.cgrand.macrovich :as macros]
                            [baby.pat.vt.macros :refer [slurp get-env]])))

(macros/deftime (defmacro slurp [thing]
                  (clojure.core/slurp thing)))

(macros/deftime (defmacro slurp-resource [thing]
                  `(clojure.core/slurp (clojure.java.io/resource ~thing))))


#?(:clj (defmacro functionize [macro]
          `(fn [& args#] (eval (cons '~macro args#)))))

#?(:clj (defmacro apply-macro [macro args]
          `(apply (functionize ~macro) ~args)))

(defmacro get-env
  ([] (medley.core/map-keys (fn [k]
                              (camel-snake-kebab.core/->kebab-case-keyword k))
                            (-> (into {} (System/getenv))
                                (into (System/getProperties)))))
  ([ks]
   `(select-keys (get-env) ~ks)))

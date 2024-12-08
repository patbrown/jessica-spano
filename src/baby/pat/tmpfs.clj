(ns  baby.pat.tmpfs
    (:require [babashka.fs :as fs]
              [clojure.java.io :as io]
              [clojure.java.shell]
              [clojure.string :as string])
    (:import [java.io File]
             [java.nio.file Files Path Paths]
             [java.nio.file.attribute FileAttribute]))

(defprotocol ITempDir
    (create [this])
    (new-file [this] [this file])
    (destroy [this] [this file]))

  (def default-temp-dir-map
    {:root "bin/resources/tmp"
     :dir-prefix "dir"
     :file-prefix "file"
     :dir (atom nil)
     :extension ".edn"})

  (defrecord TempDir [root dir-prefix file-prefix dir extension]
    ITempDir
    (create [this]
      (when-not (fs/exists? root)
        (fs/create-dirs root))
      (let [dp (str dir-prefix "-")]
        (->> (Files/createTempDirectory
              (Paths/get root (into-array String []))
              dp
              (into-array FileAttribute []))
             (reset! dir))))
    (new-file [this]
      (let [file ^File (File/createTempFile (str file-prefix "-")
                                            extension
                                            (io/file (str @dir)))
            path (.getPath file)
            [dir filename] (-> path
                               (string/split #"/"))]
        {:dir dir
         :filename filename
         :path  path
         :route (-> path
                    (string/replace #"^/tmp" ""))}))
    (new-file [this extension]
      (let [file ^File (File/createTempFile (str file-prefix "-")
                                            extension
                                            (io/file (str @dir)))
            path (fs/path file)
            parent (fs/parent file)
            nm (fs/file-name file)]
        {:dir @dir
         :filename nm
         :path parent
         :route (-> path
                    (string/replace #"^/tmp" ""))}))
    (destroy [this]
      (when (fs/exists? (str @dir))
        (-> (str @dir)
            io/file
            fs/delete-tree))))

;; (def pat (map->TempDir default-temp-dir-map))
;; (create pat)
;; (new-file pat)
;; (destroy pat)

(ns threading-macros.core)

(def products [{:id 1
                :name "Kettle"
                :type :appliance
                :stock-count 50
                :unit-size :small
                :price 100.40
                :status :active}
               {:id 2
                :name "Couch"
                :type :furniture
                :stock-count 20
                :unit-size :large
                :price 4599.00
                :status :active}
               {:id 3
                :name "Desk"
                :type :furniture
                :stock-count 40
                :unit-size :medium
                :price 1299.99
                :status :active}
               {:id 4
                :name "Toaster"
                :type :appliance
                :stock-count 100
                :unit-size :small
                :price 499.99
                :status :active}
               {:id 5
                :name "Bed"
                :type :furniture
                :stock-count 10
                :unit-size :large
                :status :active
                :price 1200
                :special? true}])
;;  ->>
(defn product-to-discount [products]
  (->> products
       (filter #(= (:type %) :furniture))
       (filter #(= (:unit-size %) :large))
       (sort-by :stock-count)
       reverse
       first))

(product-to-discount products)

;; -> 
(defn get-product-by-id [id]
  (->> products
       (filter #(= (:id %) id))
       first))
(defn sell-product [product buyer]
  (let [stock-count (:stock-count product)
        current-buyers (get :buyers product [])
        updated-buyers (conj current-buyers buyer)
        updated-stock-count (- stock-count 1)]
    (-> product
        (assoc :stock-count updated-stock-count)
        (assoc :buyers updated-buyers))))

(sell-product (get-product-by-id 2) "Dan")

;; as->
(defn create-product-review [id]
  (as-> (get-product-by-id id) p
    (:name p)
    (str "This " p " is so awesome!")))

(create-product-review 4)

;; cond-> // cond->>
(let [product (get-product-by-id 2)
      is-active? (= (:status product) :active)
      is-special? (:special? product)]
  (cond-> product
    is-active? (assoc :status :inactive)
    is-special? (dissoc :special?)))

;; some->> / some->
(some->> products
         (println "one=")
         (filter #(= (:status %) :doesntexist))
         (println "two=")
         product-to-discount
         (println "three=")
         :id
         (println "four=")
         create-product-review)









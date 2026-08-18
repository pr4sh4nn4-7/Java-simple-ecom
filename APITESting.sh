#!/usr/bin/env bash

# Create user only on first run

curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
        "userName": "john_doe",
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com",
        "phoneNumber": "9800000000",
        "address": {
          "street": "123 Main Street",
          "city": "Kathmandu",
          "state": "Bagmati",
          "country": "Nepal",
          "zipcode": "44600"
        }
      }'

# Create products every time
echo "Creating products..."

for i in {1..10}; do

  curl -X POST http://localhost:8080/api/product/create \
    -H "Content-Type: application/json" \
    -d "{
        \"name\": \"Wireless Mouse $i\",
        \"description\": \"Ergonomic wireless mouse $i\",
        \"price\": 1499.99,
        \"stockQuantity\": 50,
        \"category\": \"Electronics\",
        \"imageUrl\": \"https://example.com/images/wireless-mouse.jpg\"
      }"

  echo

done

echo "Products created."

# Add to cart every time
echo "Adding to cart..."

for i in $(seq 1 5); do

  curl --location 'http://localhost:8080/api/cart/create' \
    --header 'X-USER-ID: 1' \
    --header 'Content-Type: application/json' \
    --data '{
        "productId": 2,
        "quantity": 5
      }'

  echo

done

echo "All items added."

# Remove from cart every time
echo "Removing from cart..."

for i in $(seq 1 4); do

  curl --location --request DELETE \
    'http://localhost:8080/api/cart/remove/2' \
    --header 'X-USER-ID: 1'

  echo

done

for i in $(seq 1 5); do

  curl --location 'http://localhost:8080/api/cart/create' \
    --header 'X-USER-ID: 1' \
    --header 'Content-Type: application/json' \
    --data '{
        "productId": 2,
        "quantity": 5
      }'

  echo

done

curl http://localhost:8080/api/cart -H "X-USER-ID: 1"

echo "Done."

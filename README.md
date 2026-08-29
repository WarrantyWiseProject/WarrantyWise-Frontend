# WarrantyWise-Frontend
Android frontend for WarrantyWise, a smart warranty management application built using Kotlin and XML to store product details, track warranty periods, manage receipts, and receive expiry reminders.

## Backend connection

The app uses the FastAPI backend from `WarrantyWise-Backend`. Set `API_BASE_URL` in `gradle.properties` or pass it to Gradle with `-PAPI_BASE_URL=...`. The default `http://10.0.2.2:8000/` is for an Android emulator while the backend runs on the development computer. Use the computer's LAN IP for a physical device. The API client automatically sends the bearer token stored by `SessionStore`.

import { createBrowserRouter } from "react-router-dom";
import RootLayout from "./layouts/RootLayout";
import CreateTenantPage from "./routes/create-tenant";
import LoginPage from "./routes/login";

export const router = createBrowserRouter([
  {
    element: <RootLayout />,
    children: [
      { path: "/", element: <CreateTenantPage /> },
      { path: "/login", element: <LoginPage /> }
    ]
  }
]);
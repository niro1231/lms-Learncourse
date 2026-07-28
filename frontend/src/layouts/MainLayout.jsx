import Navbar from "../components/layout/Navbar";
import Footer from "../components/layout/Footer";
import { useLocation } from "react-router-dom";

function MainLayout({ children }) {

  const location = useLocation();

  const authPage =
    location.pathname === "/login" ||
    location.pathname === "/register";


  return (
    <div className="d-flex flex-column min-vh-100">
      {
       <Navbar />
      }
      <main className={authPage ? "" : "container my-4 flex-grow-1"}>
        {children}
      </main>
      {
        !authPage && <Footer />
      }
    </div>
  );
}
export default MainLayout;
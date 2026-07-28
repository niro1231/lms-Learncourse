import { NavLink, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    localStorage.removeItem("name");
    localStorage.removeItem("email");

    navigate("/login");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">

        <NavLink
          className="navbar-brand fw-bold"
          to="/"
        >
          LearnCourse
        </NavLink>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>


        <div
          className="collapse navbar-collapse"
          id="navbarNav"
        >

          <div className="navbar-nav ms-auto d-flex align-items-center gap-3">


            {
              token && role === "INSTRUCTOR" && (
                <NavLink
                  to="/profile"
                  className="text-white"
                  style={{ textDecoration: "none" }}
                >

                  <div
                    className="rounded-circle bg-white text-dark d-flex justify-content-center align-items-center"
                    style={{
                      width: "38px",
                      height: "38px"
                    }}
                  >
                    <i className="bi bi-person-fill fs-5"></i>
                  </div>

                </NavLink>
              )
            }


            {
              token && (
                <button
                  className="btn btn-light"
                  onClick={handleLogout}
                >
                  Logout
                </button>
              )
            }


          </div>

        </div>

      </div>
    </nav>
  );
}

export default Navbar;
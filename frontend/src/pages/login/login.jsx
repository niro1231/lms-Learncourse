import { Link, useNavigate } from "react-router-dom";
import { useState, useContext } from "react";
import { loginUser } from "../../service/authService";
import { AuthContext } from "../../context/AuthContext";
import loginBg from "../../assets/1.jpg";
import "./login.css";

function Login() {
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  const [form, setForm] = useState({
    email: "",
    password: ""
  });

  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await loginUser(form);

      login(response.data.token);

      localStorage.setItem("role", response.data.role);
      localStorage.setItem("userId", response.data.id);
      localStorage.setItem("name", response.data.name);
      localStorage.setItem("email", response.data.email);

      navigate("/");
    } catch (error) {
      setError("Invalid email or password");
    }
  };

  return (
    <div className="login-page" style={{ backgroundImage: `url(${loginBg})` }}>
      <div className="login-overlay">
        <div className="login-left">
          <h1>
            Unlock Your
            <span> Learning Journey </span>
            With LMS
          </h1>
          <p className="hero-description">
            Learn new skills, master new technologies,
            and grow your career with high-quality online courses.
          </p>
          <div className="features">
            <div>
              🎓 Expert Instructors
            </div>
            <div>
              🚀 Career Growth
            </div>
            <div>
              📚 Unlimited Learning
            </div>
          </div>
          <p className="hero-small">
            Join thousands of learners and start building
            your future today.
          </p>
        </div>
        <div className="login-right">
          <div className="card login-card shadow">
            <h2 className="text-center mb-4">
              Login
            </h2>
            {
              error &&
              <div className="alert alert-danger">
                {error}
              </div>
            }
            <form onSubmit={handleSubmit}>
              <input
                className="form-control mb-3"
                name="email"
                placeholder="Email"
                onChange={handleChange}
              />
              <input
                className="form-control mb-3"
                name="password"
                type="password"
                placeholder="Password"
                onChange={handleChange}
              />
              <button className="btn btn-primary w-100">
                Login
              </button>
            </form>
            <hr />
            <p className="text-center">
              Don't have account?
              <Link to="/register">
                Register
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Login;
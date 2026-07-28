import { useState } from "react";
import { Link } from "react-router-dom";
import loginBg from "../../assets/1.jpg";
import "./register.css";
import { registerUser } from "../../service/authService";

function Register() {

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    role: "",
    profileImage: null
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleImageChange = (e) => {
    setForm({
      ...form,
      profileImage: e.target.files[0]
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const formData = new FormData();

      formData.append("name", form.name);
      formData.append("email", form.email);
      formData.append("password", form.password);
      formData.append("role", form.role);

      if (form.profileImage) {
        formData.append("profileImage", form.profileImage);
      }

      await registerUser(formData);

      setMessage("Registration successful");

    } catch (error) {
      console.log(error);
      setMessage("Registration failed");
    }
  };


  return (
    <div
      className="register-page"
      style={{
        backgroundImage: `url(${loginBg})`
      }}
    >

      <div className="register-overlay">

        <div className="card shadow register-card">

          <h2 className="text-center mb-4">
            Create Account
          </h2>

          {
            message &&
            <div className="alert alert-info">
              {message}
            </div>
          }

          <form onSubmit={handleSubmit}>

            <div className="mb-3">
              <label className="form-label">
                Name
              </label>

              <input
                className="form-control"
                name="name"
                value={form.name}
                onChange={handleChange}
                required
              />
            </div>


            <div className="mb-3">
              <label className="form-label">
                Email
              </label>

              <input
                className="form-control"
                name="email"
                type="email"
                value={form.email}
                onChange={handleChange}
                required
              />
            </div>


            <div className="mb-3">
              <label className="form-label">
                Password
              </label>

              <input
                className="form-control"
                name="password"
                type="password"
                value={form.password}
                onChange={handleChange}
                required
              />
            </div>


            <div className="mb-3">
              <label className="form-label">
                Profile Image
              </label>

              <input
                className="form-control"
                type="file"
                accept="image/*"
                onChange={handleImageChange}
              />
            </div>


            <div className="mb-3">

              <label className="form-label">
                Select Role
              </label>

              <div className="d-flex gap-4">

                <div className="form-check">

                  <input
                    className="form-check-input"
                    type="radio"
                    name="role"
                    value="STUDENT"
                    checked={form.role === "STUDENT"}
                    onChange={handleChange}
                    required
                  />

                  <label className="form-check-label">
                    Student
                  </label>

                </div>


                <div className="form-check">

                  <input
                    className="form-check-input"
                    type="radio"
                    name="role"
                    value="INSTRUCTOR"
                    checked={form.role === "INSTRUCTOR"}
                    onChange={handleChange}
                  />

                  <label className="form-check-label">
                    Instructor
                  </label>

                </div>

              </div>

            </div>


            <button className="btn btn-success w-100">
              Register
            </button>

          </form>


          <hr />

          <p className="text-center">
            Already have account?
            <Link to="/login">
              Login
            </Link>
          </p>

        </div>

      </div>

    </div>
  );
}

export default Register;
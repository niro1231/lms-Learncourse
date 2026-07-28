import { BrowserRouter, Routes, Route } from "react-router-dom";

import MainLayout from "./layouts/MainLayout";
import ProtectedRoute from "./components/common/auth/ProtectedRoute";
import Home from "./pages/home/home";
import Login from "./pages/login/login";
import Register from "./pages/register/register";
import NotFound from "./pages/notfound/notfound";
import CourseDetails from "./pages/course/CourseDetails";
import Profile from "./pages/profile/Profile";
import AdminDashboard from "./pages/admin/AdminDashboard";
import InstructorCourses from "./pages/instructor/InstructorCourses";

function App() {
  return (
    <BrowserRouter>
      <MainLayout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="*" element={<NotFound />} />
          <Route path="/profile" element={<Profile />} />
          <Route
            path="/admin"
            element={
              <ProtectedRoute allowedRole="ADMIN">
                <AdminDashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/instructors/:id"
            element={<InstructorCourses />}
          />
          <Route path="/courses/:id" element={<CourseDetails />} />
        </Routes>
      </MainLayout>
    </BrowserRouter>
  );
}

export default App;
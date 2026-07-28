import { useEffect, useState } from "react";
import {
    getAdminDashboard,
    getAllUsers,
    deleteUser,
    deleteCourse,
    uploadAdvertisement
} from "../../service/adminService";
import { getAllCourses } from "../../service/courseService";
import "./AdminDashboard.css";

function AdminDashboard() {

    const [dashboard, setDashboard] = useState({});
    const [users, setUsers] = useState([]);
    const [courses, setCourses] = useState([]);
    const [activeTab, setActiveTab] = useState("users");
    const [search, setSearch] = useState("");
    const [showAdModal, setShowAdModal] = useState(false);
    const [adTitle, setAdTitle] = useState("");
    const [adImage, setAdImage] = useState(null);

    useEffect(() => {
        loadDashboard();
        loadUsers();
        loadCourses();
    }, []);

    const loadDashboard = async () => {
        const response = await getAdminDashboard();
        setDashboard(response.data);
    };

    const loadUsers = async () => {
        const response = await getAllUsers();
        setUsers(response.data);
    };

    const loadCourses = async () => {
        const response = await getAllCourses();
        setCourses(response.data);
    };

    const handleDeleteUser = async (id) => {
        if (window.confirm("Delete this user?")) {
            await deleteUser(id);
            loadUsers();
            loadDashboard();
        }
    };

    const handleDeleteCourse = async (id) => {
        if (window.confirm("Delete this course?")) {
            await deleteCourse(id);
            loadCourses();
            loadDashboard();
        }
    };

    const handleAdvertisementUpload = async () => {
        if (!adTitle || !adImage) {
            alert("Please enter title and select image");
            return;
        }
        const formData = new FormData();
        formData.append(
            "title",
            adTitle
        );
        formData.append(
            "image",
            adImage
        );
        await uploadAdvertisement(formData);
        alert("Advertisement added successfully");
        setAdTitle("");
        setAdImage(null);
        setShowAdModal(false);
    };

    const filteredUsers = users.filter(user =>
        user.name.toLowerCase().includes(search.toLowerCase()) ||
        user.email.toLowerCase().includes(search.toLowerCase())
    );

    const filteredCourses = courses.filter(course =>
        course.title.toLowerCase().includes(search.toLowerCase()) ||
        course.category.toLowerCase().includes(search.toLowerCase())
    );

    return (
        <div className="container py-4">

            <div className="admin-top-card">

                <div className="admin-header">
                    <h2 className="admin-title">
                        Admin Dashboard
                    </h2>
                    <div className="header-line"></div>
                </div>

                <div className="dashboard-grid">

                    <div className="dashboard-card">
                        <span>Total Users</span>
                        <h2>{dashboard.totalUsers}</h2>
                    </div>

                    <div className="dashboard-card">
                        <span>Total Courses</span>
                        <h2>{dashboard.totalCourses}</h2>
                    </div>

                    <div className="dashboard-card split-card">

                        <div className="split-item">
                            <span>Total Students</span>
                            <h2>{dashboard.totalStudents}</h2>
                        </div>

                        <div className="divider"></div>

                        <div className="split-item">
                            <span>Total Instructors</span>
                            <h2>{dashboard.totalInstructors}</h2>
                        </div>

                    </div>

                </div>

            </div>


            <div className="admin-table-card">

                <div className="admin-tab-row">

                    <div className="admin-tabs">
                        <button
                            className={activeTab === "users" ? "tab-btn active" : "tab-btn"}
                            onClick={() => {
                                setActiveTab("users");
                                setSearch("");
                            }}
                        >
                            Manage Users
                        </button>
                        <button
                            className={activeTab === "courses" ? "tab-btn active" : "tab-btn"}
                            onClick={() => {
                                setActiveTab("courses");
                                setSearch("");
                            }}
                        >
                            Manage Courses
                        </button>
                        <button
                            className="add-ad-btn"
                            onClick={() => setShowAdModal(true)}
                        >
                            + Add Advertisement
                        </button>
                    </div>

                    <div className="search-box">

                        <input
                            type="text"
                            placeholder={activeTab === "users" ? "Search users..." : "Search courses..."}
                            value={search}
                            onChange={(e) => setSearch(e.target.value)}
                        />

                    </div>

                </div>


                {
                    activeTab === "users" && (

                        <div className="admin-box">

                            <h3>
                                Manage Users
                            </h3>

                            <div className="table-container">

                                <table className="admin-table">

                                    <thead>

                                        <tr>
                                            <th>User</th>
                                            <th>Email</th>
                                            <th>Role</th>
                                            <th>Action</th>
                                        </tr>

                                    </thead>

                                    <tbody>

                                        {
                                            filteredUsers.map(user => (

                                                <tr key={user.id}>

                                                    <td>
                                                        <div className="user-name">
                                                            {user.name}
                                                        </div>
                                                    </td>

                                                    <td>
                                                        {user.email}
                                                    </td>

                                                    <td>
                                                        <span className="role-badge">
                                                            {user.role}
                                                        </span>
                                                    </td>

                                                    <td>
                                                        <button
                                                            className="delete-btn"
                                                            onClick={() => handleDeleteUser(user.id)}
                                                        >
                                                            Delete
                                                        </button>
                                                    </td>

                                                </tr>

                                            ))
                                        }

                                    </tbody>

                                </table>

                            </div>

                        </div>

                    )
                }



                {
                    activeTab === "courses" && (

                        <div className="admin-box">

                            <h3>
                                Manage Courses
                            </h3>

                            <div className="table-container">

                                <table className="admin-table">

                                    <thead>

                                        <tr>
                                            <th>Course</th>
                                            <th>Category</th>
                                            <th>Instructor</th>
                                            <th>Action</th>
                                        </tr>

                                    </thead>

                                    <tbody>

                                        {
                                            filteredCourses.map(course => (

                                                <tr key={course.id}>

                                                    <td>
                                                        <div className="course-name">
                                                            {course.title}
                                                        </div>
                                                    </td>

                                                    <td>
                                                        <span className="category-badge">
                                                            {course.category}
                                                        </span>
                                                    </td>

                                                    <td>
                                                        {course.instructorName}
                                                    </td>

                                                    <td>
                                                        <button
                                                            className="delete-btn"
                                                            onClick={() => handleDeleteCourse(course.id)}
                                                        >
                                                            Delete
                                                        </button>
                                                    </td>

                                                </tr>

                                            ))
                                        }

                                    </tbody>

                                </table>

                            </div>

                        </div>

                    )
                }

            </div>
            {
                showAdModal && (
                    <div className="modal-overlay">
                        <div className="ad-modal">
                            <h3>
                                Add Advertisement
                            </h3>
                            <input
                                type="text"
                                placeholder="Advertisement title"
                                value={adTitle}
                                onChange={(e) => setAdTitle(e.target.value)}
                            />
                            <input
                                type="file"
                                accept="image/*"
                                onChange={(e) => setAdImage(e.target.files[0])}
                            />
                            <div className="modal-actions">
                                <button
                                    className="cancel-btn"
                                    onClick={() => setShowAdModal(false)}
                                >
                                    Cancel
                                </button>
                                <button
                                    className="save-ad-btn"
                                    onClick={handleAdvertisementUpload}
                                >
                                    Upload
                                </button>
                            </div>
                        </div>
                    </div>
                )
            }
        </div>
    );
}

export default AdminDashboard;
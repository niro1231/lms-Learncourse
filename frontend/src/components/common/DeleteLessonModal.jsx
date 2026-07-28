function DeleteLessonModal({onClose,onConfirm}){

    return(
        <div className="modal-backdrop">

            <div className="delete-modal">

                <div className="modal-header">
                    <h5>
                        Delete Lesson
                    </h5>

                    <button
                        className="btn-close"
                        onClick={onClose}
                    />
                </div>


                <div className="modal-body">

                    <p>
                        Are you sure you want to delete this lesson?
                    </p>

                    <small className="text-danger">
                        This action cannot be undone.
                    </small>

                </div>


                <div className="modal-footer">

                    <button
                        className="btn btn-secondary"
                        onClick={onClose}
                    >
                        Cancel
                    </button>


                    <button
                        className="btn btn-danger"
                        onClick={onConfirm}
                    >
                        Delete
                    </button>

                </div>

            </div>

        </div>
    );
}

export default DeleteLessonModal;
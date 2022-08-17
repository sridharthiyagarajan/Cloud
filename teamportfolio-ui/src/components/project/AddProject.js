import React, {useState} from "react";
import ProjectDataService from "../../services/ProjectService";

const AddProject = () => {

    const initialProjectState = {
        id : null,
        title: "",
        description: "",
        startDate: "",
        endate: ""
    };

    const [project, setProject] = useState(initialProjectState);
    const[submitted, setSubmitted] = useState(false);

    const handleInputChange = event => {
        const {name, value} = event.target;
        setProject({...project, [name]:value});
    }

    const saveProject = () => {
        var data = {
            title: project.title,
            description: project.description,
            startDate: project.startDate,
            endDate: project.endDate
        }

        ProjectDataService.create(data)
        .then(response => {
            setProject({
                title : response.data.title,
                description : response.data.description,
                startDate: response.startDate,
                endDate: response.endDate
            });
            setSubmitted(true);
            console.log(response.data);
        })
        .catch(e => {
            console.log(e);
        });
    };

    const newProject = () => {
        setProject(initialProjectState);
        setSubmitted(false);
    };

    return (

        <div className="submit-form">
            {
                submitted ? (
                <div>
                    <h4>You submitted successfully!</h4>
                    <button className="btn btn-success" onClick={newProject}>Add</button>
                </div>
                ) : (
                    <div>
                        <div className="form-group">
                            <label htmlFor="title">Title</label>
                            <input type="text" className="form-control" id="title" required value={project.title} onChange={handleInputChange} name="title" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="description">Description</label>
                            <input type="text" className="form-control" id="description" required value={project.description} onChange={handleInputChange} name="description" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="startDate">Start Date</label>
                            <input type="text" className="form-control" id="startDate" required value={project.startDate} onChange={handleInputChange} name="startDate" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="endDate">Start Date</label>
                            <input type="text" className="form-control" id="endDate" required value={project.endDate} onChange={handleInputChange} name="startDate" />
                        </div>

                        <button onClick={saveProject} className="btn btn-success">Submit</button>
                    </div>
                )
            }
        </div>
    );
};

export default AddProject;
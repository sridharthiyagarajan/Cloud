import React, {useState} from "react";
import SkillDataService from "../../services/SkillService";

const AddSkill = () => {

    const initialSkillState = {
        id : null,
        name: "",
        description: ""
    };

    const [skill, setSkill] = useState(initialSkillState);
    const[submitted, setSubmitted] = useState(false);

    const handleInputChange = event => {
        const {name, value} = event.target;
        setSkill({...skill, [name]:value});
    }

    const saveSkill = () => {
        var data = {
            name: skill.name,
            description: skill.description
        }

        SkillDataService.create(data)
        .then(response => {
            setSkill({
                name : response.data.name,
                description : response.data.description
            });
            setSubmitted(true);
            console.log(response.data);
        })
        .catch(e => {
            console.log(e);
        });
    };

    const newSkill = () => {
        setSkill(initialSkillState);
        setSubmitted(false);
    };

    return (

        <div className="submit-form">
            {
                submitted ? (
                <div>
                    <h4>You submitted successfully!</h4>
                    <button className="btn btn-success" onClick={newSkill}>Add</button>
                </div>
                ) : (
                    <div>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <input type="text" className="form-control" id="name" required value={skill.name} onChange={handleInputChange} name="name" />
                        </div>

                        <div className="form-group">
                            <label htmlFor="description">Description</label>
                            <input type="text" className="form-control" id="description" required value={skill.description} onChange={handleInputChange} name="description" />
                        </div>

                        <button onClick={saveSkill} className="btn btn-success">Submit</button>
                    </div>
                )
            }
        </div>
    );
};

export default AddSkill;
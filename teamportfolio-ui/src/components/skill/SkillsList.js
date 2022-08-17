import React, {useState, useEffect, useMemo, useRef} from "react";
import SkillDataService from "../../services/SkillService";
import { useTable } from "react-table";

const SkillsList = (props) => {

    const [skills, setSkills] = useState([]);
    const [searchName, setSearchName]  = useState("");
    const skillsRef = useRef();

    skillsRef.current = skills;

    useEffect (() => {
        retrieveSkills();
    }, []);

    const onChangeSearchName = (e) => {
        const searchName = e.target.value;
        setSearchName(searchName);
    };

    const retrieveSkills = () => {
        SkillDataService.getAll()
        .then((response) => {
            setSkills(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };
        
    const refreshList = () => {
        retrieveSkills();
    };

    const findByName = () => {
        SkillDataService.findByName(searchName)
        .then((response) => {
            setSkills(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };

    const openSkill = (rowIndex) => {
        const id = skillsRef.current[rowIndex].id;
        props.history.push("/skills/" + id);
    };

    const columns = useMemo(
        () => [
            {
                Header: "Name",
                accessor: "name",
            },
            {
                Header: "Description",
                accessor: "description",
            },
            {
                Header: "Actions",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return(
                        <div>
                            <span onClick={() => openSkill(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteSkill(rowIdx)}>
                                <i className="fas fa-trash action"></i>
                            </span>
                        </div>
                    );
                },   
            },
        ],
        []
    );

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow
    } = useTable({
        columns,
        data: skills,
    });

    const deleteSkill = (rowIndex) => {
        const id = skillsRef.current[rowIndex].id;
    
        SkillDataService.remove(id)
          .then((response) => {
            props.history.push("/skills");
    
            let newSkills = [...skillsRef.current];
            newSkills.splice(rowIndex, 1);
    
            setSkills(newSkills);
          })
          .catch((e) => {
            console.log(e);
          });
      };

    return (
        <div className="list row">
          <div className="col-md-8">
            <div className="input-group mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Search by name"
                value={searchName}
                onChange={onChangeSearchName}
              />
              <div className="input-group-append">
                <button
                  className="btn btn-outline-secondary"
                  type="button"
                  onClick={findByName}
                >
                  Search
                </button>
              </div>
            </div>
          </div>
          <div className="col-md-12 list">
            <table
              className="table table-striped table-bordered"
              {...getTableProps()}
            >
              <thead>
                {headerGroups.map((headerGroup) => (
                  <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map((column) => (
                      <th {...column.getHeaderProps()}>
                        {column.render("Header")}
                      </th>
                    ))}
                  </tr>
                ))}
              </thead>
              <tbody {...getTableBodyProps()}>
                {rows.map((row, i) => {
                  prepareRow(row);
                  return (
                    <tr {...row.getRowProps()}>
                      {row.cells.map((cell) => {
                        return (
                          <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                        );
                      })}
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      );
};

export default SkillsList;
import React, {useState, useEffect, useMemo, useRef} from "react";
import ProjectDataService from "../../services/ProjectService";
import { useTable } from "react-table";

const ProjectsList = (props) => {

    const [projects, setProjects] = useState([]);
    const [searchTitle, setSearchTitle]  = useState("");
    const projectsRef = useRef();

    projectsRef.current = projects;

    useEffect (() => {
        retrieveProjects();
    }, []);

    const onChangeSearchTitle = (e) => {
        const searchTitle = e.target.value;
        setSearchTitle(searchTitle);
    };

    const retrieveProjects = () => {
        ProjectDataService.getAll()
        .then((response) => {
            setProjects(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };
        
    const refreshList = () => {
        retrieveProjects();
    };

    const findByTitle = () => {
        ProjectDataService.findByTitle(searchTitle)
        .then((response) => {
            setProjects(response.data);
        })
        .catch((e) => {
            console.log(e);
        });
    };

    const openProject = (rowIndex) => {
        const id = projectsRef.current[rowIndex].id;
        props.history.push("/projects/" + id);
    };

    const columns = useMemo(
        () => [
            {
                Header: "Title",
                accessor: "title",
            },
            {
                Header: "Description",
                accessor: "description",
            },
            {
              Header: "Start Date",
              accessor: "startDate",
            },
            {
              Header: "End Date",
              accessor: "endDate",
            },
            {
                Header: "Actions",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return(
                        <div>
                            <span onClick={() => openProject(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteProject(rowIdx)}>
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
        data: projects,
    });

    const deleteProject = (rowIndex) => {
        const id = projectsRef.current[rowIndex].id;
    
        ProjectDataService.remove(id)
          .then((response) => {
            props.history.push("/projects");
    
            let newProjects = [...projectsRef.current];
            newProjects.splice(rowIndex, 1);
    
            setProjects(newProjects);
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
                placeholder="Search by title"
                value={searchTitle}
                onChange={onChangeSearchTitle}
              />
              <div className="input-group-append">
                <button
                  className="btn btn-outline-secondary"
                  type="button"
                  onClick={findByTitle}
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

export default ProjectsList;
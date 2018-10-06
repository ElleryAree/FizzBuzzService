import React, {Component} from 'react';
import {ProgressBar} from "react-bootstrap";
import './App.css';

const {List} = require('immutable');


function AnswerRow(props) {
    const {name, numbers, supplier} = props;

    return (
        <tr>
            <th>{name}</th>
            {numbers.map(number => <td key={name + "." + number.turn} className={number.correct ? "success" : "danger"}>{supplier(number)}</td>)}
            <td className="last-column">{' '}</td>
        </tr>
    )
}


function Numbers(props) {
    const {numbers} = props;

    return (
        <div className="panel panel-default">
            <div className="panel-heading">
                <h3 className="panel-title">Answers so far</h3>
            </div>
            <div className="panel-body number-container">
                <table className="table">
                    <tbody>
                    <AnswerRow name="Number" numbers={numbers} supplier={number => number.number} />
                    <AnswerRow name="Answer" numbers={numbers} supplier={number => number.actual} />
                    <AnswerRow name="Correct" numbers={numbers} supplier={number => number.expected} />
                    </tbody>
                </table>
            </div>
        </div>
    )
}

class Timer extends Component {
    constructor(props) {
        super(props);
        const {ticks} = props;

        this.state = { left: ticks, ticks};

        this.tick = this.tick.bind(this);
    }

    componentDidMount() {
        this.timer = setInterval(this.tick, 100);
    }

    componentWillUnmount() {
        clearInterval(this.timer);
    }

    tick() {
        const left = this.state.left - 1;
        if (left <= 0) {
            this.props.onTimeout();
        }

        this.setState({left});
    }

    render() {
        const {left, ticks} = this.state;
        const now = 100 * left / ticks;

        return <ProgressBar now={now} />;
    }
}

function Current(props) {
    const {number, onAnswer, onTimeout} = props;

    return (
        <div className="well">
            <Timer ticks={50} onTimeout={onTimeout}/>
            <p>Current number: {number} </p>
            <div className="btn-group" role="group" aria-label="...">
                <button type="button" className="btn btn-default" onClick={()=>onAnswer("Fizz")}>Fizz</button>
                <button type="button" className="btn btn-default" onClick={()=>onAnswer("Buzz")}>Buzz</button>
                <button type="button" className="btn btn-default" onClick={()=>onAnswer("FizzBuzz")}>FizzBuzz</button>
                <button type="button" className="btn btn-default" onClick={()=>onAnswer(number)}>{number}</button>
            </div>
        </div>
    )
}

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            status: "NOT_LOADED",
            paused: false,

            numbers: new List(),

            current: null,
            answer: null,
            turn: 0
        };

        this.processAnswer = this.processAnswer.bind(this);
        this.onTimeout = this.onTimeout.bind(this);
        this.handleStop = this.handleStop.bind(this);
    }

    generateNumber() {
        return Math.floor(Math.random() * 101);
    }

    componentWillMount() {
        this.fetchNext();
    }

    onTimeout() {
        this.processAnswer("-")
    }

    handleStop() {
        const {paused} = this.state;

        if (paused) {
            this.fetchNext();
        }

        this.setState({paused: !paused})
    }

    processAnswer(answer) {
        const {answer: expected, current, turn} = this.state;
        let {numbers} = this.state;

        const correct = answer == expected;
        numbers = numbers.push({number: current, actual: answer, expected, correct, turn});

        this.setState({
            numbers,
            turn: turn + 1
        });

        this.fetchNext();
    }

    fetchNext() {
        this.setState({status: "LOADING"});

        const next = this.generateNumber();

        const updateEdits = (data) => this.setState({
            current: data.number,
            answer: data.response,
            status: "LOADED"
        });

        fetch("/fizz?number=" + next, {
            headers: {
                'Accept': 'application/json'
            }
        }).then(function(response) {
            if (!response.ok) {
                throw Error(response);
            }
            return response.json();
        }).then(function(response) {
            updateEdits(response)
        }).catch(function(error) {
            console.log(error);
        });
    }

    render() {
        const {status, numbers, current, paused} = this.state;

        let currentPanel;

        if (paused) {
            currentPanel = <div className="well">Not running</div>
        }
        else if (status === "LOADED") {
            currentPanel = <Current key={current} number={current} onAnswer={this.processAnswer} onTimeout={this.onTimeout}/>
        } else {
            currentPanel = <div className="well">Loading next number</div>
        }

        return (
            <div className="App">
                <nav className="navbar navbar-default">
                    <div className="container-fluid">
                        <div className="navbar-header">
                            <div className="navbar-brand">
                                Fizz/Buzz
                            </div>
                            <div className="navbar-form navbar-left">
                                <button className="btn btn-default" onClick={this.handleStop}>{paused ? "Run" : "Stop"}</button>
                            </div>
                        </div>
                    </div>
                </nav>
                <div className="container-fluid">
                    <Numbers numbers={numbers}/>
                    {currentPanel}
                </div>
            </div>
        );
    }
}

export default App;

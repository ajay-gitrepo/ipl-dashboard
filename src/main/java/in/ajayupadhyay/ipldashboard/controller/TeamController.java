package in.ajayupadhyay.ipldashboard.controller;

import in.ajayupadhyay.ipldashboard.model.Match;
import in.ajayupadhyay.ipldashboard.model.Team;
import in.ajayupadhyay.ipldashboard.repository.MatchRepository;
import in.ajayupadhyay.ipldashboard.repository.TeamRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }


    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        Pageable pageable= PageRequest.of(0,4);
        //team.setMatches(matchRepository.getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable)) ;
        team.setMatches(matchRepository.findLatestMatchesbyTeam(teamName,4));

        return team;
    }
    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year){
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year +1, 1, 1);
        /*return this.matchRepository.getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
                teamName,
                startDate,
                endDate,
                teamName,
                startDate,
                endDate
        );*/
        return this.matchRepository.getMatchesByTeamBetweenDates(
                teamName,
                startDate,
                endDate
        );
    }
}
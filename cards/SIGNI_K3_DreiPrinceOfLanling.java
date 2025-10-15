package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_DreiPrinceOfLanling extends Card {
    
    public SIGNI_K3_DreiPrinceOfLanling()
    {
        setImageSets("WXDi-P01-044");
        
        setOriginalName("ドライ＝ランリョウオー");
        setAltNames("ドライランリョウオー Dorai Ranryouoo");
        setDescription("jp",
                "=T <<DIAGRAM>>\n" +
                "^U：このシグニがにアタックしたとき、あなたか対戦相手のデッキの上からカードを５枚トラッシュに置く。\n" +
                "@E %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。あなたのトラッシュにカードが２０枚以上ある場合、代わりにターン終了時まで、それのパワーを－10000する。"
        );
        
        setName("en", "Lanling Type: Drei");
        setDescription("en",
                "=T <<DIAGRAM>>\n" +
                "^U: Whenever this SIGNI attacks, put the top five cards of your deck or your opponent's deck into the trash.\n" +
                "@E %K: Target SIGNI on your opponent's field gets --5000 power until end of turn. If you have twenty or more cards in your trash, it gets --10000 power until end of turn instead."
        );
        
        setName("en_fan", "Drei-Prince of Lanling");
        setDescription("en_fan",
                "=T <<DIAGRAM>>\n" +
                "^U: Whenever this SIGNI attacks, put the top 5 cards of your or your opponent's deck into the trash.\n" +
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power. If there are 20 or more cards in your trash, it gets --10000 power instead."
        );
        
		setName("zh_simplified", "DREI=兰陵王");
        setDescription("zh_simplified", 
                "=T<<DIAGRAM>>\n" +
                "^U:当这只精灵攻击时，从你或对战对手的牌组上面把5张牌放置到废弃区。\n" +
                "@E %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。你的废弃区的牌在20张以上的场合，作为替代，直到回合结束时为止，其的力量-10000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.DIAGRAM) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1)
            {
                millDeck(5);
            } else {
                millDeck(getOpponent(), 5);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, getTrashCount(getOwner()) < 20 ? -5000 : -10000, ChronoDuration.turnEnd());
        }
    }
}

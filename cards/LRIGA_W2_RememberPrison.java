package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_W2_RememberPrison extends Card {

    public LRIGA_W2_RememberPrison()
    {
        setImageSets("WX24-P4-039");

        setOriginalName("リメンバ・プリズン");
        setAltNames("リメンバプリズン Rimenba Purizun");
        setDescription("jp",
                "@E：数字１つを宣言する。このターン、対戦相手は宣言された数字と同じレベルのシグニでアタックできない。"
        );

        setName("en", "Remember Prison");
        setDescription("en",
                "@E: Declare a number. This turn, your opponent can't attack with SIGNI of the same level as the declared number."
        );

		setName("zh_simplified", "忆·监牢");
        setDescription("zh_simplified", 
                "@E :数字1种宣言。这个回合，对战对手的与宣言数字相同等级的精灵不能攻击。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            int number = playerChoiceNumber(0,1,2,3,4,5) - 1;
            
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_ATTACK, getOpponent(), ChronoDuration.turnEnd(), data ->
                CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) && data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() == number ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
            );
        }
    }
}

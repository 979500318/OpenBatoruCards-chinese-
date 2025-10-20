package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_CodeArtNEckCooler extends Card {

    public SIGNI_B2_CodeArtNEckCooler()
    {
        setImageSets("WX25-P2-086");

        setOriginalName("コードアート　Nッククーラー");
        setAltNames("コードアートエネッククーラー Koodo Aato Enekku Kuuraa Neck Cooler");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたの場に黒の＜電機＞のシグニがある場合、カードを１枚引く。\n" +
                "$$2このターンにあなたがスペルを使用していた場合、対戦相手は手札を１枚捨てる。" +
                "~#：対戦相手のルリグ１体を対象とし、それをダウンする。"
        );

        setName("en", "Code Art N Eck Cooler");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there is a black <<Electric Machine>> SIGNI on your field, draw 1 card.\n" +
                "$$2 If you used a spell this turn, your opponent discards 1 card from their hand." +
                "~#Target 1 of your opponent's LRIG, and down it."
        );

		setName("zh_simplified", "必杀代号 挂脖空调");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 你的场上有黑色的<<電機>>精灵的场合，抽1张牌。\n" +
                "$$2 这个回合你把魔法使用过的场合，对战对手把手牌1张舍弃。" +
                "~#对战对手的分身1只作为对象，将其横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                if(new TargetFilter().own().SIGNI().withColor(CardColor.BLACK).withClass(CardSIGNIClass.ELECTRIC_MACHINE).getValidTargetsCount() > 0)
                {
                    draw(1);
                }
            } else if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                discard(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}

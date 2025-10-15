package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_AspidocheloneSnakeTurtleOfDemonicSeas extends Card {

    public SIGNI_B2_AspidocheloneSnakeTurtleOfDemonicSeas()
    {
        setImageSets("WXK01-087");

        setOriginalName("魔海の蛇亀　アスピドケロン");
        setAltNames("マカイノヘビガメアスピドケロン Makai no Hebigame Asupidokeron");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、あなたの手札が１枚以下の場合、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Aspidochelone, Snake Turtle of Demonic Seas");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are 1 or less cards in your hand, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "魔海的蛇龟 阿斯匹德克隆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，你的手牌在1张以下的场合，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(getHandCount(getOwner()) <= 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }
    }
}

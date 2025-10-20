package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_RackhamSailorOfDemonicSeas extends Card {

    public SIGNI_B3_RackhamSailorOfDemonicSeas()
    {
        setImageSets("WXK01-052");

        setOriginalName("魔海の船員　ラカム");
        setAltNames("マカイノセンインラカム Makai no Senin Rakamu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－4000する。\n" +
                "@E：あなたの手札が０枚の場合、カードを１枚引く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それをダウンし凍結する。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Rackham, Sailor of Demonic Seas");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --4000 power.\n" +
                "@E: If there are 0 cards in your hand, draw 1 card." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and down and freeze it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "魔海的船员 拉克姆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-4000。\n" +
                "@E :你的手牌在0张的场合，抽1张牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，将其横置并冻结。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(8000);

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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, -4000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            if(getHandCount(getOwner()) == 0)
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                down(target);
                freeze(target);
            } else {
                enerCharge(1);
            }
        }
    }
}

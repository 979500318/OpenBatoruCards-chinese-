package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G3_ChironPhantomApparition extends Card {

    public SIGNI_G3_ChironPhantomApparition()
    {
        setImageSets("WXK01-058");

        setOriginalName("幻怪　ケイローン");
        setAltNames("ゲンカイケイローン Genkai Keiroon");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワーが10000以上の場合、カードを１枚引き、ターン終了時まで、このシグニは【ランサー】を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー12000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Chiron, Phantom Apparition");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 10000 or more, draw 1 card, and until end of turn, this SIGNI gains [[Lancer]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 12000 or more, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "幻怪 半人马");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在10000以上的场合，抽1张牌，直到回合结束时为止，这只精灵得到[[枪兵]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的力量12000以上的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(getPower().getValue() >= 10000)
            {
                draw(1);
                
                attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(12000,0)).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}

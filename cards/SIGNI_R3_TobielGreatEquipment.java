package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R3_TobielGreatEquipment extends Card {
    
    public SIGNI_R3_TobielGreatEquipment()
    {
        setImageSets("WXDi-D01-015");
        
        setOriginalName("大装　トビエル");
        setAltNames("タイソウトビエル Taisou Tobieru");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの他のシグニ１体を対象とし、ターン終了時まで、それらのパワーを＋５０００する。\n" +
                "@E @[手札を２枚捨てる]@：カードを２枚引く。" +
                "~#：@[@|どちらか１つを選ぶ。|@]@\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Tobiel, Full Armed");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, another target SIGNI on your field gets +5000 power until end of turn.\n" +
                "@E @[Discard two cards]@: Draw two cards." +
                "~#@[@|Choose 1:|@]@\n" +
                "$$1 Vanish target upped SIGNI on your opponent's field.\n" +
                "$$2 Draw a card."
        );
        
        setName("en_fan", "Tobiel, Great Equipment");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your other SIGNI, and until end of turn, that SIGNI gets +5000 power.\n" +
                "@E @[Discard 2 cards from your hand]@: Draw 2 cards." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "大装 托碧耶尔");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的其他的精灵1只作为对象，直到回合结束时为止，其的力量+5000。\n" +
                "@E 手牌2张舍弃:抽2张牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().except(getCardIndex())).get();
            gainPower(cardIndex, 5000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            draw(2);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(cardIndex);
            } else {
                draw(1);
            }
        }
    }
}

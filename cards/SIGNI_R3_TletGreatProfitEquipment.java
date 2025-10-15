package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_TletGreatProfitEquipment extends Card {
    
    public SIGNI_R3_TletGreatProfitEquipment()
    {
        setImageSets("WXDi-D03-016");
        
        setOriginalName("大得装　トレット");
        setAltNames("タイトクソウトレット Taitokusou Toretto");
        setDescription("jp",
                "@E %R %X：あなたのトラッシュからレベル１の赤のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：@[@|どちらか１つを選ぶ。|@]@\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Letti, Heavy Armed");
        setDescription("en",
                "@E %R %X: Add target red level one SIGNI from your trash to your hand." +
                "~#Choose one --\n" +
                "$$1 Vanish target upped SIGNI on your opponent's field.\n" +
                "$$2 Draw a card."
        );
        
        setName("en_fan", "Tlet, Great Profit Equipment");
        setDescription("en_fan",
                "@E %R %X: Target 1 level 1 red SIGNI from your trash, and add it to your hand." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "大得装 手甲");
        setDescription("zh_simplified", 
                "@E %R%X:从你的废弃区把等级1的红色的精灵1张作为对象，将其加入手牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.RED).withLevel(1).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}

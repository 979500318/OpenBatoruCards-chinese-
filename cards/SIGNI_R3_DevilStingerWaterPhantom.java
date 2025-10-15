package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R3_DevilStingerWaterPhantom extends Card {
    
    public SIGNI_R3_DevilStingerWaterPhantom()
    {
        setImageSets("WXDi-D07-018");
        
        setOriginalName("幻水　オニオコゼ");
        setAltNames("ゲンスイオニオコゼ Gensui Oniokoze");
        setDescription("jp",
                "@E @[手札を３枚まで捨てる]@：この方法で捨てたカードの枚数に等しい枚数のカードを引く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Devil Stinger, Phantom Aquatic Beast");
        setDescription("en",
                "@E @[Discard up to three cards]@: Draw cards equal to the number of cards discarded this way." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Devil Stinger, Water Phantom");
        setDescription("en_fan",
                "@E @[Discard up to 3 cards from your hand]@: Draw cards equal to the number of cards discarded this way." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "幻水 日本鬼鮋");
        setDescription("zh_simplified", 
                "@E 手牌3张最多舍弃:抽这个方法舍弃的牌的张数相等张数的牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            
            registerEnterAbility(new DiscardCost(0,3), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            if(!getAbility().getCostPaidData().isEmpty() && getAbility().getCostPaidData().get() != null)
            {
                draw(getAbility().getCostPaidData().size());
            }
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

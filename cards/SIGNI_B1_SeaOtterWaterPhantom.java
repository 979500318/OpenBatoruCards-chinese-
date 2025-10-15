package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_SeaOtterWaterPhantom extends Card {

    public SIGNI_B1_SeaOtterWaterPhantom()
    {
        setImageSets("WX24-P3-074");

        setOriginalName("幻水　ラッコ");
        setAltNames("ゲンスイラッコ Gensui Rakko");
        setDescription("jp",
                "@E @[手札から＜水獣＞のシグニを２枚まで捨てる]@：この方法で捨てたカード１枚につきカードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Sea Otter, Water Phantom");
        setDescription("en",
                "@E @[Discard up to 2 <<Water Beast>> SIGNI from your hand]@: Draw 1 card for each card discarded this way." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "幻水 海獭");
        setDescription("zh_simplified", 
                "@E 从手牌把<<水獣>>精灵2张最多舍弃:依据这个方法舍弃的牌的数量，每有1张就抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(0,2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.WATER_BEAST)), this::onEnterEff);
            
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_W2_DwarfPufferfishWaterPhantom extends Card {

    public SIGNI_W2_DwarfPufferfishWaterPhantom()
    {
        setImageSets("SPDi01-131", Mask.IGNORE+"SPDi01-131P");

        setOriginalName("幻水　アペニーパファー");
        setAltNames("ゲンスイアペニーパファー Gensui Apeniipafaa");
        setDescription("jp",
                "@E %X：あなたのデッキの上からカードを３枚見る。その中からあなたの場にいるルリグと共通する色を持つカード１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );

        setName("en", "Dwarf Pufferfish, Water Phantom");
        setDescription("en",
                "@E %X: Look at the top 3 cards of your deck. Reveal 1 card that shares a common color with a LRIG on your field from among them, add it to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's LRIG, and until end of turn, it gains:" +
                "@>@C@#: Can't attack."
        );

		setName("zh_simplified", "幻水 迷你河豚鱼");
        setDescription("zh_simplified", 
                "@E %X:从你的牌组上面看3张牌。从中把持有与你的场上的分身共通颜色的牌1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withColor(
                getLRIGs(getOwner()).stream().map(c -> c.getIndexedInstance().getColor()).toArray(CardDataColor[]::new)
            ).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}

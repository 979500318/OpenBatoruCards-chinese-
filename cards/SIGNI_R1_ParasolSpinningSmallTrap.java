package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.game.FieldZone;

public final class SIGNI_R1_ParasolSpinningSmallTrap extends Card {

    public SIGNI_R1_ParasolSpinningSmallTrap()
    {
        setImageSets("WX24-P3-067");

        setOriginalName("小罠　カサマワシ");
        setAltNames("ショウビンカサマワシ Shoubin Kasamawashi");
        setDescription("jp",
                "@A $T1 @[手札から＜トリック＞のシグニを１枚捨てる]@：あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで【マジックボックス】としてあなたのシグニゾーンに設置し、残りを好きな順番でデッキの一番下に置く。" +
                "~#：手札を１枚捨て、カードを３枚引く。"
        );

        setName("en", "Parasol Spinning, Small Trap");
        setDescription("en",
                "@A $T1 @[Discard 1 <<Trick>> SIGNI from your hand]@: Look at the top 3 cards of your deck. Put up to 1 card from among them onto 1 of your SIGNI zones as a [[Magic Box]], and put the rest on the bottom of your deck in any order." +
                "~#Discard 1 card from your hand, and draw 3 cards."
        );

		setName("zh_simplified", "小罠 转伞");
        setDescription("zh_simplified", 
                "@A $T1 从手牌把<<トリック>>精灵1张舍弃:从你的牌组上面看3张牌。从中把牌1张最多作为[[魔术箱]]在你的精灵区设置，剩下的任意顺序放置到牌组最下面。" +
                "~#手牌1张舍弃，抽3张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
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

            ActionAbility act = registerActionAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.TRICK)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ZONE).own().fromLooked()).get();
            putAsMagicBox(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            discard(1);
            draw(3);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExcludeCost;

public final class SIGNI_W1_CodeAntiMedjed extends Card {

    public SIGNI_W1_CodeAntiMedjed()
    {
        setImageSets("WXDi-P11-053");

        setOriginalName("コードアンチ　メジェド");
        setAltNames("コードアンチメジェド Koodo Anchi Mejedo");
        setDescription("jp",
                "@A %W %W %X @[トラッシュにある《コードアンチ　メジェド》４枚をゲームから除外する]@：対戦相手のシグニ１体を対象とし、それを手札に戻す。この能力はこのカードがトラッシュにありあなたのトラッシュに《コードアンチ　メジェド》が４枚ある場合にしか使用できない。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Medjed, Code: Anti");
        setDescription("en",
                "@A %W %W %X @[Remove four \"Medjed, Code: Anti\" in your trash from the game]@: Return target SIGNI on your opponent's field to its owner's hand. This ability can only be used if this card is in your trash and four \"Medjed, Code: Anti\" are in your trash." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code Anti Medjed");
        setDescription("en_fan",
                "@A %W %W %X @[Exclude 4 \"Code Anti Medjed\" cards in your trash from the game]@: Target 1 of your opponent's SIGNI, and return it to their hand. This ability can only be used if this card is in your trash and there are 4 \"Code Anti Medjed\" in your trash." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "古兵代号 梅杰德");
        setDescription("zh_simplified", 
                "@A %W %W%X废弃区的《コードアンチ　メジェド》4张从游戏除外:对战对手的精灵1只作为对象，将其返回手牌。这个能力只有在这张牌在废弃区且你的废弃区的《コードアンチ　メジェド》在4张的场合才能使用。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.WHITE, 2) + Cost.colorless(1)),
                new ExcludeCost(4, new TargetFilter().SIGNI().withName("コードアンチ　メジェド").fromTrash())
            ), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setActiveLocation(CardLocation.TRASH);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onActionEffCond()
        {
            return new TargetFilter().own().SIGNI().withName("コードアンチ　メジェド").fromTrash().getValidTargetsCount() == 4 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(cardIndex != null)
            {
                reveal(cardIndex);
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}

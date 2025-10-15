package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.LifeBurstAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class LRIG_B3_EldoraXMarkNu extends Card {
    
    public LRIG_B3_EldoraXMarkNu()
    {
        setImageSets("WXDi-P08-008", "WXDi-P08-008U", Mask.IGNORE+"WXDi-P180P");
        
        setOriginalName("エルドラ×マークν");
        setAltNames("エルドラマークニュー Erudora Maaku Nyuu EldoraxMark V Eldora x Mark V");
        setDescription("jp",
                "@C：あなたのライフクロスとチェックゾーンにある##を持たないカードは@>~#：どちらか１つを選ぶ。\n$$1カードを１枚引く。\n$$2【エナチャージ１】@@@@を得る。\n" +
                "@E：対戦相手のライフクロスの一番上を公開する。あなたはそのカードと対戦相手のデッキの一番上のカードを入れ替えてもよい。\n" +
                "@A $G1 %B0：あなたのライフクロス１枚をクラッシュする。そうした場合、あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );
        
        setName("en", "Eldora × Mark ν");
        setDescription("en",
                "@C: Cards in your Life Cloth and Check Zone without ## gain@>~#Choose one -- \n$$1 Draw a card. \n$$2 [[Ener Charge 1]].@@@@" +
                "@E: Reveal the top card of your opponent's Life Cloth. You may swap that card with the top card of your opponent's deck.\n" +
                "@A $G1 %B0: Crush one of your Life Cloth. If you do, shuffle your deck and add the top card of your deck to your Life Cloth."
        );
        
        setName("en_fan", "Eldora×Mark Nu");
        setDescription("en_fan",
                "@C: All of the cards in your life cloth and check zone without ## @[Life Burst]@ gain:" +
                "@>~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 [[Ener Charge 1]].@@@@" +
                "@E: Reveal the top card of your opponent's life cloth. You may exchange that card with the top card of your opponent's deck.\n" +
                "@A $G1 %B0: Crush 1 of your life cloth. If you do, shuffle your deck and add the top card of it to life cloth."
        );
        
		setName("zh_simplified", "艾尔德拉×ν式");
        setDescription("zh_simplified", 
                "@C 你的生命护甲和检查区的不持有##的牌得到##\n" +
                "@>:以下选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 [[能量填充1]]@@\n" +
                "@E :对战对手的生命护甲最上面公开。你可以把那张牌和对战对手的牌组最上面的牌交换。\n" +
                "@A $G1 %B0:你的生命护甲1张击溃。这样做的场合，你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().fromLocation(CardLocation.LIFE_CLOTH, CardLocation.CHECK_ZONE),
                new AbilityGainModifier(this::onConstEffSharedModGetSample)
            );
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private Ability onConstEffSharedModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getAbilityList().stream().filter(ability -> ability instanceof LifeBurstAbility && !ability.isDisabled()).
                    findFirst().isEmpty() ? cardIndex.getIndexedInstance().registerLifeBurstAbility(this::onAttachedLifeBurstEff) : null;
        }
        private void onAttachedLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal(getOpponent(), CardLocation.LIFE_CLOTH);
            
            if(cardIndex != null && playerChoiceActivate() && addToLifeCloth(getOpponent(), 1).get() != null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            } else {
                addToLifeCloth(cardIndex);
            }
        }
        
        private void onActionEff()
        {
            if(crush(getOwner()).get() != null)
            {
                shuffleDeck();
                addToLifeCloth(1);
            }
        }
    }
}

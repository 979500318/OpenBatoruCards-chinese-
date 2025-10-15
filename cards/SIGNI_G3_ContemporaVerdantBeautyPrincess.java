package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_G3_ContemporaVerdantBeautyPrincess extends Card {

    public SIGNI_G3_ContemporaVerdantBeautyPrincess()
    {
        setImageSets("WXDi-P09-043", Mask.IGNORE+"PR-Di044");

        setOriginalName("翠美姫　コンテンポラ");
        setAltNames("スイビキコンテンポラ Suibiki Kontempora");
        setDescription("jp",
                "@C：対戦相手のターンの間、対戦相手のシグニは[[シャドウ（レベル１）]]と[[シャドウ（レベル２）]]を得る。\n\n" +
                "@U：このカードが対戦相手の効果によっていずれかの領域からトラッシュに置かれたとき、%Xを支払ってもよい。そうした場合、このシグニをトラッシュから手札に加える。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Contempora, Jade Beauty Queen");
        setDescription("en",
                "@C: During your opponent's turn, SIGNI on your field gains [[Shadow -- Level one]] and [[Shadow -- Level two]].\n\n" +
                "@U: When this card is put into the trash from a zone by your opponent's effect, you may pay %X. If you do, add this card from your trash to your hand." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Contempora, Verdant Beauty Princess");
        setDescription("en_fan",
                "@C: During your opponent's turn, your SIGNI gain [[Shadow (level 1)]] and [[Shadow (level 2)]].\n\n" +
                "@U: When this card is put from any zone into your trash by your opponent's effect, you may pay %X. If you do, add this SIGNI from your trash to your hand." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]."
        );

		setName("zh_simplified", "翠美姬 当代");
        setDescription("zh_simplified", 
                "@C $TP :你的精灵得到[[暗影（等级1）]]和[[暗影（等级2）]]。\n" +
                "@U :当这张牌因为对战对手的效果从任一个领域放置到废弃区时，可以支付%X。这样做的场合，这张精灵从废弃区加入手牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI(),
                new AbilityGainModifier(this::onConstEffMod1GetSample),
                new AbilityGainModifier(this::onConstEffMod2GetSample)
            );

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setActiveLocation(
                    CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT,
                    CardLocation.ENER,CardLocation.DECK_MAIN, CardLocation.HAND,CardLocation.LIFE_CLOTH
            );
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod1GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEff1AddCond));
        }
        private ConditionState onAttachedStockEff1AddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() == 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEff2AddCond, 1));
        }
        private ConditionState onAttachedStockEff2AddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() == 2 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH && payEner(Cost.colorless(1)))
            {
                addToHand(getCardIndex());
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}

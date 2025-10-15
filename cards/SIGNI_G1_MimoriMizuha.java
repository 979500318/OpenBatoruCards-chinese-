package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G1_MimoriMizuha extends Card {

    public SIGNI_G1_MimoriMizuha()
    {
        setImageSets("WXDi-CP02-087");

        setOriginalName("水羽ミモリ");
        setAltNames("ミズハミモリ Mizuha Mimori");
        setDescription("jp",
                "@E：あなたのエナゾーンに＜ブルアカ＞のカードが５枚以上ある場合、あなたのエナゾーンから＜ブルアカ＞のシグニを１枚まで対象とし、それを場に出す。" +
                "~{{C：[[シャドウ（パワー8000以下のシグニ）]]@@" +
                "~#：【エナチャージ２】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Mizuha Mimori");
        setDescription("en",
                "@E: If there are five or more <<Blue Archive>> cards in your Ener Zone, put up to one target <<Blue Archive>> SIGNI from your Ener Zone onto your field.~{{C: [[Shadow -- SIGNI with power 8000 or less]].@@" +
                "~#[[Ener Charge 2]]. Then, add up to one target SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Mimori Mizuha");
        setDescription("en_fan",
                "@E: If there are 5 or more <<Blue Archive>> cards in your ener zone, target up to 1 <<Blue Archive>> SIGNI from your ener zone, and put it onto the field." +
                "~{{C: [[Shadow (SIGNI with power 8000 or less)]]@@" +
                "~#[[Ener Charge 2]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "水羽三森");
        setDescription("zh_simplified", 
                "@E :你的能量区的<<ブルアカ>>牌在5张以上的场合，从你的能量区把<<ブルアカ>>精灵1张最多作为对象，将其出场。\n" +
                "~{{C:[[暗影（力量8000以下的精灵）]]@@" +
                "~#[[能量填充2]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            ConstantAbility cont = registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            if(new TargetFilter().own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner().getValidTargetsCount() >= 5)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner().playable()).get();
                putOnField(target);
            }
        }

        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(2);

            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.PutInEnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_G3_IzunaKuda extends Card {

    public SIGNI_G3_IzunaKuda()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-057");

        setOriginalName("久田イズナ");
        setAltNames("クダイズナ Kuda Izuna");
        setDescription("jp",
                "@E @[手札から＜ブルアカ＞のカードを１枚エナゾーンに置く]@：あなたのエナゾーンから＜ブルアカ＞のカード１枚を対象とし、それを手札に加える。\n" +
                "@A @[エナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置く]@：ターン終了時まで、このシグニは@>@C：このシグニは正面のシグニのパワーが12000以上であるかぎり、【アサシン】を得る。@@を得る。" +
                "~{{A %G0：あなたのエナゾーンからこのカードを場に出す。"
        );

        setName("en", "Kuda Izuna");
        setDescription("en",
                "@E @[Put a <<Blue Archive>> card from your hand into your Ener Zone]@: Add target <<Blue Archive>> card from your Ener Zone to your hand.\n@A @[Put two <<Blue Archive>> cards from your Ener Zone into your trash]@: This SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI has power 12000 or more, this SIGNI gains [[Assassin]].@@until end of turn. ~{{A %G0: Put this card from your Ener Zone onto your field."
        );
        
        setName("en_fan", "Izuna Kuda");
        setDescription("en_fan",
                "@E @[Put 1 <<Blue Archive>> card from your hand into the ener zone]@: Target 1 <<Blue Archive>> card from your ener zone, and add it to your hand.\n" +
                "@A @[Put 2 <<Blue Archive>> cards from your ener zone into the trash]@: Until end of turn, this SIGNI gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has power 12000 or more, this SIGNI gains [[Assassin]].@@" +
                "~{{A %G0: Put this card from your ener zone onto the field."
        );

		setName("zh_simplified", "久田泉奈");
        setDescription("zh_simplified", 
                "@E 从手牌把<<ブルアカ>>牌1张放置到能量区:从你的能量区把<<ブルアカ>>牌1张作为对象，将其加入手牌。\n" +
                "@A 从能量区把<<ブルアカ>>牌2张放置到废弃区:直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在12000以上时，得到[[暗杀]]。@@\n" +
                "。（持有[[暗杀]]的精灵攻击，不与正面的精灵进行战斗，给予对战对手伤害）\n" +
                "~{{A%G0:从你的能量区把这张牌出场。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new PutInEnerCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromHand()), this::onEnterEff);
            
            registerActionAbility(new TrashCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);
            act2.setActiveLocation(CardLocation.ENER);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
            addToHand(target);
        }
        
        private void onActionEff1()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);

            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() != null &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI().getIndexedInstance().getPower().getValue() >= 12000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityAssassin());
        }
        
        private ConditionState onActionEff2Cond()
        {
            return isPlayable() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff2()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER)
            {
                putOnField(getCardIndex());
            }
        }
    }
}

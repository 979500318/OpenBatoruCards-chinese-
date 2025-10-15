package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_LOVITTHEDOORPhantomBeastDeity extends Card {

    public SIGNI_W3_LOVITTHEDOORPhantomBeastDeity()
    {
        setImageSets("WXDi-P15-057", "WXDi-P15-057P");

        setOriginalName("幻獣神　LOVIT//THE DOOR");
        setAltNames("ゲンジュウシンラビットザドアー Genjuushin Rabitto Za Doaa");
        setDescription("jp",
                "@C：このシグニと同じシグニゾーンに【ゲート】があるかぎり、このシグニのパワーは＋3000され、このシグニは@>@C：対戦相手のターンの間、【シャドウ】を得る。@@を得る。\n" +
                "@U：あなたのターン終了時、あなたの場に【ゲート】がある場合、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、%Xを支払ってもよい。そうした場合、それを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "LOVIT//THE DOOR, Phantom Beast Deity");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gets +3000 power and gains@>@C: During your opponent's turn, this SIGNI gains [[Shadow]].@@@U: At the end of your turn, if there is a [[Gate]] on your field, you may pay %X. If you do, add target SIGNI with a #G from your trash to your hand." +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "LOVIT//THE DOOR, Phantom Beast Deity");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gets +3000 power, and it gains:" +
                "@>@C: During your opponent's turn, this SIGNI gains [[Shadow]].@@" +
                "@U: At the end of your turn, if there is a [[Gate]] on your field, target 1 #G @[Guard]@ SIGNI from your trash, and you may pay %X. If you do, add it to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "幻兽神 LOVIT//THE DOOR");
        setDescription("zh_simplified", 
                "@C :与这只精灵的相同精灵区有[[大门]]时，这只精灵的力量+3000，这只精灵得到\n" +
                "@>@C :对战对手的回合期间，得到[[暗影]]。@@\n" +
                "@U 你的回合结束时，你的场上有[[大门]]的场合，从你的废弃区把持有#G的精灵1张作为对象，可以支付%X。这样做的场合，将其加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000), new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(this::onAttachedConstEff, new AbilityGainModifier(this::onAttachedConstEffModGetSample));
        }
        private ConditionState onAttachedConstEff()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
                
                if(target != null && payEner(Cost.colorless(1)))
                {
                    addToHand(target);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}

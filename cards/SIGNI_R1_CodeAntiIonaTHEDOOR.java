package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
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
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R1_CodeAntiIonaTHEDOOR extends Card {

    public SIGNI_R1_CodeAntiIonaTHEDOOR()
    {
        setImageSets("WXDi-P16-064");

        setOriginalName("コードアンチ　イオナ//THE DOOR");
        setAltNames("コードアンチイオナザドアー Koodo Anchi Iona Za Doaa");
        setDescription("jp",
                "@U：あなたのターン終了時、対戦相手のエナゾーンにカードが２枚以上ある場合、あなたのシグニの下から＜解放派＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n\n" +
                "@C：このカードの上にある＜解放派＞のシグニは@>@U：あなたのアタックフェイズ開始時、%R %X %Xを支払ってもよい。そうした場合、ターン終了時まで、このシグニは【アサシン】を得る。@@を得る。"
        );

        setName("en", "Iona//THE DOOR, Code: Anti");
        setDescription("en",
                "@U: At the end of your turn, if there are two or more cards in your opponent's Ener Zone, you may put a <<Liberation Division>> SIGNI underneath a SIGNI on your field into its owner's trash. If you do, your opponent chooses a card from their Ener Zone and puts it into their trash.\n\n@C: The <<Liberation Division>> SIGNI on top of this card gains@>@U: At the beginning of your attack phase, you may pay %R %X %X. If you do, this SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Code Anti Iona//THE DOOR");
        setDescription("en_fan",
                "@U: At the end of your turn, if there are 2 or more cards in your opponent's ener zone, you may put 1 <<Liberation Faction>> SIGNI from under your SIGNI into the trash. If you do, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n\n" +
                "@C: The <<Liberation Faction>> SIGNI on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, you may pay %R %X %X. If you do, until end of turn, this SIGNI gains [[Assassin]]."
        );

		setName("zh_simplified", "古兵代号 伊绪奈//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，对战对手的能量区的牌在2张以上的场合，可以从你的精灵的下面把<<解放派>>精灵1张放置到废弃区。这样做的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@C :这张牌上面的<<解放派>>精灵得到\n" +
                "@>@U :你的攻击阶段开始时，可以支付%R%X %X。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.ANCIENT_WEAPON);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).withUnderType(CardUnderCategory.UNDER)).get();
                
                if(trash(target))
                {
                    CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                    trash(cardIndex);
                }
            }
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(2)))
            {
                attachAbility(getAbility().getSourceCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
    }
}

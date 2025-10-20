package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B1_AllosPirulukTHEDOORPhantomInsect extends Card {

    public SIGNI_B1_AllosPirulukTHEDOORPhantomInsect()
    {
        setImageSets("WXDi-P15-064");

        setOriginalName("幻蟲　アロス・ピルルク//THE DOOR");
        setAltNames("ゲンチュウアロスピルルクザドアー Genchuu Arosu Piruruku Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札から＜解放派＞のシグニを１枚捨ててもよい。そうした場合、対戦相手は手札を１枚捨てる。\n\n" +
                "@C：このカードの上にある＜解放派＞のシグニは@>@U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。@@を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Allos Piruluk//THE DOOR, Phantom Insect");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a <<Liberation Division>> SIGNI. If you do, your opponent discards a card.\n\n@C: The <<Liberation Division>> SIGNI on top of this card gains@>@U: At the beginning of your attack phase, you may discard a card. If you do, your opponent discards a card at random.@@" +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "Allos Piruluk//THE DOOR, Phantom Insect");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may discard 1 <<Liberation Faction>> SIGNI from your hand. If you do, your opponent discards 1 card from their hand.\n\n" +
                "@C: The <<Liberation Faction>> SIGNI on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and your opponent discards it.@@" +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "幻虫 阿洛斯·皮璐璐可//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从手牌把<<解放派>>精灵1张舍弃。这样做的场合，对战对手把手牌1张舍弃。\n" +
                "@C :这张牌的上面的<<解放派>>精灵得到\n" +
                "@>@U :你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。@@" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.MISFORTUNE_INSECT);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION)).get() != null)
            {
                discard(getOpponent(), 1);
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
            if(discard(0,1).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}

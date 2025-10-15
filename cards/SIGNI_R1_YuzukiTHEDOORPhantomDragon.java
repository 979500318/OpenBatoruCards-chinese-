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
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_YuzukiTHEDOORPhantomDragon extends Card {

    public SIGNI_R1_YuzukiTHEDOORPhantomDragon()
    {
        setImageSets("WXDi-P15-060");

        setOriginalName("幻竜　遊月//THE DOOR");
        setAltNames("ゲンリュウユヅキザドアー Genryuu Yuzuki Za Doaa");
        setDescription("jp",
                "@C：あなたの場にあるシグニの下にカードがあるかぎり、このシグニのパワーは＋4000される。\n\n" +
                "@C：このカードの上にある＜解放派＞のシグニは@>@C：あなたのアタックフェイズ開始時、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。@@を得る。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Yuzuki//THE DOOR, Phantom Dragon");
        setDescription("en",
                "@C: As long as there is a card underneath SIGNI on your field, this SIGNI gets +4000 power.\n\n@C: The <<Liberation Division>> SIGNI on top of this card gains@>@U: At the beginning of your attack phase, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash.@@" +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Yuzuki//THE DOOR, Phantom Dragon");
        setDescription("en_fan",
                "@C: While there is a card under 1 of your SIGNI, this SIGNI gets +4000 power.\n\n" +
                "@C: The <<Liberation Faction>> SIGNI on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash.@@" +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );

		setName("zh_simplified", "幻龙 游月//THE DOOR");
        setDescription("zh_simplified", 
                "@C :你的场上的精灵有下面的牌时，这只精灵的力量+4000。\n" +
                "@C :这张牌的上面的<<解放派>>精灵得到\n" +
                "@>@U :你的攻击阶段开始时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。@@" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.DRAGON_BEAST);
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

            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(4000));

            ConstantAbility cont2 = registerConstantAbility(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).over(cardId), new AbilityGainModifier(this::onConstEff2ModGetSample));
            cont2.setActiveUnderFlags(CardUnderCategory.UNDER);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().SIGNI().withCardsUnder(CardUnderCategory.UNDER).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            trash(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}

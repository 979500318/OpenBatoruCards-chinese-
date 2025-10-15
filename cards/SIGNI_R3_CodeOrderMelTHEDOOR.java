package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_R3_CodeOrderMelTHEDOOR extends Card {

    public SIGNI_R3_CodeOrderMelTHEDOOR()
    {
        setImageSets("WXDi-P15-049", "WXDi-P15-049P");

        setOriginalName("コードオーダー　メル//THE DOOR");
        setAltNames("コードオーダーメルザドアー Koodo Oodaa Meru Za Doaa");
        setDescription("jp",
                "=R あなたの＜解放派＞のシグニ１体の上に置く\n\n" +
                "@U：このシグニが場を離れたとき、あなたのトラッシュからこのシグニの下にあったシグニ１枚を対象とし、%X %Xを支払ってもよい。そうした場合、それをダウン状態で場に出す。それの@E能力は発動しない。\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。"
        );

        setName("en", "Mel//THE DOOR, Code: Order");
        setDescription("en",
                "=R Place on top of a <<Liberation Division>> SIGNI on your field. \n@U: When this SIGNI leaves the field, you may pay %X %X. If you do, put target SIGNI that was underneath this SIGNI from your trash onto your field downed. The @E abilities of SIGNI put onto the field this way do not activate.\n@U: At the beginning of your attack phase, if there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash."
        );
        
        setName("en_fan", "Code Order Mel//THE DOOR");
        setDescription("en_fan",
                "=R Put on 1 of your <<Liberation Faction>> SIGNI\n\n" +
                "@U: When this SIGNI leaves the field, target 1 SIGNI from your trash that was under this SIGNI, and you may pay %X %X. If you do, put it onto the field downed. Its @E abilities don't activate.\n" +
                "@U: At the beginning of your attack phase, if there are 2 or more cards in your opponent's ener zone, your opponent puts 1 card from their ener zone into the trash."
        );

		setName("zh_simplified", "点单代号 梅露//THE DOOR");
        setDescription("zh_simplified", 
                "=R在你的<<解放派>>精灵1只的上面放置\n" +
                "@U :当这只精灵离场时，从你的废弃区把这只精灵的下面原有的精灵1张作为对象，可以支付%X %X。这样做的场合，将其以#D状态出场。其的@E能力不能发动。\n" +
                "@U :你的攻击阶段开始时，对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(13000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withClass(CardSIGNIClass.LIBERATION_FACTION));
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private DataTable<CardIndex> dataUnder;
        private ConditionState onAutoEff1Cond()
        {
            if(CardLocation.isSIGNI(EventMove.getDataMoveLocation())) return ConditionState.BAD;

            dataUnder = new TargetFilter().own().SIGNI().under(getCardIndex()).getExportedData();
            return ConditionState.OK;
        }
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().fromTrash().match(dataUnder)).get();

            if(target != null && payEner(Cost.colorless(2)))
            {
                putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2)
            {
                CardIndex target = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(target);
            }
        }
    }
}

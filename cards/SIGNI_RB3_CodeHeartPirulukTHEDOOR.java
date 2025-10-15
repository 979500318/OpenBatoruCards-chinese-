package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_RB3_CodeHeartPirulukTHEDOOR extends Card {

    public SIGNI_RB3_CodeHeartPirulukTHEDOOR()
    {
        setImageSets("WXDi-P16-056", "WXDi-P16-056P");

        setOriginalName("コードハート　ピルルク//THE DOOR");
        setAltNames("コードハートピルルクザドアー Koodo Haato Piruruku Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。あなたの場にある＜解放派＞のシグニの下にカードが合計４枚以上ある場合、代わりにターン終了時まで、それのパワーを－8000する。\n" +
                "@E：あなたのトラッシュから＜解放派＞のシグニ１枚を対象とし、それをこのシグニの下に置く。"
        );

        setName("en", "Piruluk//THE DOOR, Code: Heart");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a card. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn. If there are a total of four or more cards underneath <<Liberation Division>> SIGNI on your field, it gets --8000 power until end of turn instead.\n@E: Put target <<Liberation Division>> SIGNI from your trash under this SIGNI."
        );
        
        setName("en_fan", "Code Heart Piruluk//THE DOOR");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --5000 power. If there are a total of 4 or more cards under your <<Liberation Faction>> SIGNI on the field, until end of turn, it gets --8000 power instead.\n" +
                "@E: Target 1 <<Liberation Faction>> SIGNI from your trash, and put it under this SIGNI."
        );

		setName("zh_simplified", "爱心代号 皮璐璐可//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-5000。你的场上的<<解放派>>精灵的下面的牌合计4张以上的场合，作为替代，直到回合结束时为止，其的力量-8000。\n" +
                "@E :从你的废弃区把<<解放派>>精灵1张作为对象，将其放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.ELECTRIC_MACHINE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();

            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, new TargetFilter().own().under(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).getExportedData()).getValidTargetsCount() < 4 ? -5000 : -8000, ChronoDuration.turnEnd());
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromTrash()).get();
            attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
        }
    }
}

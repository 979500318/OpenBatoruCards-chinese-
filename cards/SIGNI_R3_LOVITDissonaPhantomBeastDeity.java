package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_LOVITDissonaPhantomBeastDeity extends Card {

    public SIGNI_R3_LOVITDissonaPhantomBeastDeity()
    {
        setImageSets("WXDi-P13-047", "WXDi-P13-047P", "PR-Di030");

        setOriginalName("幻獣神　ＬＯＶＩＴ//ディソナ");
        setAltNames("ゲンジュウシンラビットディソナ Genjuushin Rabitto Disona");
        setDescription("jp",
                "@C：あなたのターンの間、あなたの他の#Sのシグニのパワーを＋3000する。\n" +
                "@U $T1：あなたのターンの間、対戦相手のルリグがグロウしたとき、対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置く。\n" +
                "@E @[エナゾーンから#Sのカード２枚をトラッシュに置く]@：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "LOVIT//Dissona, Phantom Beast Deity");
        setDescription("en",
                "@C: During your turn, other #S SIGNI on your field get +3000 power.\n@U $T1: During your turn, when a LRIG on your opponent's field grows, put target card from your opponent's Ener Zone into their trash. \n@E @[Put two #S cards from your Ener Zone into your trash]@: Vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "LOVIT//Dissona, Phantom Beast Deity");
        setDescription("en_fan",
                "@C: During your turn, all of your other #S @[Dissona]@ SIGNI get +3000 power.\n" +
                "@U $T1: During your turn, when 1 of your opponent's LRIG grows, target 1 card from your opponent's ener zone, and put it into the trash.\n" +
                "@E @[Put 2 #S @[Dissona]@ cards from your ener zone into the trash]@: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );

		setName("zh_simplified", "幻兽神 LOVIT//失调");
        setDescription("zh_simplified", 
                "@C 你的回合期间，你的其他的#S的精灵的力量+3000。\n" +
                "@U $T1 :你的回合期间，当对战对手的分身成长时，从对战对手的能量区把1张牌作为对象，将其放置到废弃区。\n" +
                "@E 从能量区把#S的牌2张放置到废弃区:对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().dissona().except(cardId), new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.GROW, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new TrashCost(2, new TargetFilter().dissona().fromEner()), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
            trash(target);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}

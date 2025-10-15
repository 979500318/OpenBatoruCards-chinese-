package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G1_WarewolfDissonaPhantomBeast extends Card {

    public SIGNI_G1_WarewolfDissonaPhantomBeast()
    {
        setImageSets("WXDi-P12-078");

        setOriginalName("幻獣　ワウルフ//ディソナ");
        setAltNames("ゲンジュウワウルフディソナ Genjuu Uwaurufu Disona");
        setDescription("jp",
                "@C：このシグニは各ターンに一度しかアタックできない。\n" +
                "@E @[エナゾーンから#Sのカード１枚をトラッシュに置く]@：ターン終了時まで、このシグニは@>@C：このシグニは正面のシグニがレベル１であるかぎり、【ランサー】を得る。@@を得る。"
        );

        setName("en", "Wawolf//Dissona, Phantom Terra Beast");
        setDescription("en",
                "@C: This SIGNI can only attack once each turn.\n@E @[Put a #S card from your Ener Zone into your trash]@: This SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI is level one, this SIGNI gains [[Lancer]].@@until end of turn.\n"
        );
        
        setName("en_fan", "Werewolf//Dissona, Phantom Beast");
        setDescription("en_fan",
                "@C: This SIGNI can only attack once per turn.\n" +
                "@E @[Put 1 #S @[Dissona]@ card from your ener zone into the trash]@: Until end of turn, this SIGNI gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI is level 1, this SIGNI gains [[Lancer]]."
        );

		setName("zh_simplified", "幻兽 狼人//失调");
        setDescription("zh_simplified", 
                "@C :这只精灵在各回合只能攻击一次。\n" +
                "@E 从能量区把#S的牌1张放置到废弃区:直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵在等级1时，得到[[枪兵]]。@@\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ATTACK, this::onConstEffModRuleCheck));

            registerEnterAbility(new TrashCost(new TargetFilter().dissona().fromEner()), this::onEnterEff);
        }

        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && event.getCallerCardIndex() == getCardIndex()) > 0 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() != null &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI().getIndexedInstance().getLevel().getValue() == 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
    }
}

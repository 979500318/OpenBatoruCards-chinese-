package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_G2_AlraunePhantomApparition extends Card {

    public SIGNI_G2_AlraunePhantomApparition()
    {
        setImageSets("WXK01-060");

        setOriginalName("幻怪　アルラウネ");
        setAltNames("ゲンカイアルラウネ Genkai Aruraune");
        setDescription("jp",
                "@E：以下の２つから１つを選ぶ。\n" +
                "$$1このターン、あなたが次に緑のアーツを使用する場合、それの使用コストは%X減る。\n" +
                "$$2このシグニと隣接するあなたのシグニを２体まで対象とし、ターン終了時まで、それらのパワーをそれぞれ＋3000する。"
        );

        setName("en", "Alraune, Phantom Apparition");
        setDescription("en",
                "@E: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 This turn, the use cost of the next green ARTS you use is reduced by %X.\n" +
                "$$2 Target up to 2 of your SIGNI adjacent to this SIGNI, and until end of turn, they get +3000 power."
        );

		setName("zh_simplified", "幻怪 花妖");
        setDescription("zh_simplified", 
                "@E :从以下的2种选1种。\n" +
                "$$1 这个回合，你下一次把绿色的必杀使用的场合，其的使用费用减%X。\n" +
                "$$2 与这只精灵相邻的你的精灵2只最多作为对象，直到回合结束时为止，这些的力量各+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(playerChoiceMode() == 1)
            {
                int cacheUsedARTSCount = GameLog.getTurnRecordsCount(event ->
                    event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) &&
                    event.getCaller().getColor().matches(CardColor.GREEN)
                );
                
                ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().ARTS().anyLocation(),
                    new CostModifier(() -> new EnerCost(Cost.colorless(1)), ModifierMode.REDUCE)
                );
                attachedConst.setCondition(cardIndex ->
                    GameLog.getTurnRecordsCount(event ->
                        event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) &&
                        event.getCaller().getColor().matches(CardColor.GREEN)
                    ) == cacheUsedARTSCount ? ConditionState.OK : ConditionState.BAD
                );
                
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            } else {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.PLUS).own().SIGNI().or(new TargetFilter().left(), new TargetFilter().right()));
                gainPower(data, 3000, ChronoDuration.turnEnd());
            }
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_X_SanctumTowerCollapse extends Card {

    public ARTS_X_SanctumTowerCollapse()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-036");

        setOriginalName("サンクトゥムタワー崩壊");
        setAltNames("サンクトゥムタワーホウカイ Sankutumu Tawaa Houkai");
        setDescription("jp",
                "あなたの場に＜ブルアカ＞のシグニがある場合、対戦相手のルリグかシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Sanctum Tower Collapse");
        setDescription("en",
                "If there is a <<Blue Archive>> SIGNI on your field, target 1 of your opponent's LRIG or SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack."
        );

		setName("zh_simplified", "圣所之塔倒塌");
        setDescription("zh_simplified", 
                "你的场上有<<ブルアカ>>精灵的场合，对战对手的分身或精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.ARTS);
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }
        
        private ConditionState onARTSEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onARTSEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
    }
}

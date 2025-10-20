package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.SIGNIZonePosition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckCanNewlyPutSIGNIOnField;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SIGNI_K2_MyuFessonePhantomInsect extends Card {

    public SIGNI_K2_MyuFessonePhantomInsect()
    {
        setImageSets("WXDi-P14-068");

        setOriginalName("幻蟲　ミュウ//フェゾーネ");
        setAltNames("ゲンチュウミュウフェゾーネ Genchuu Myuu Fezoone");
        setDescription("jp",
                "@C：対戦相手は中央のシグニゾーンにレベル３以上のシグニを新たに配置できない。\n" +
                "@A #D：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Myu//Fesonne, Phantom Insect");
        setDescription("en",
                "@C: Your opponent cannot put a level three or more SIGNI into their center SIGNI Zone.\n@A #D: Target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Myu//Fessone, Phantom Insect");
        setDescription("en_fan",
                "@C: Your opponent can't newly put a level 3 or higher SIGNI onto their center SIGNI zone.\n" +
                "@A #D: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "幻虫 缪//音乐节");
        setDescription("zh_simplified", 
                "@C :对战对手不能在中央的精灵区把等级3以上的精灵新配置。\n" +
                "@A 横置:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_NEWLY_PUT_SIGNI_ON_FIELD, TargetFilter.HINT_OWNER_OP, this::onConstEffModRuleCheck));
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return RuleCheckCanNewlyPutSIGNIOnField.getDataZoneSIGNIPosition(data) == SIGNIZonePosition.CENTER &&
                   data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() >= 3 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}

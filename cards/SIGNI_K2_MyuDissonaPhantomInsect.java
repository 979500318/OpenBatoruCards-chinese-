package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SIGNI_K2_MyuDissonaPhantomInsect extends Card {

    public SIGNI_K2_MyuDissonaPhantomInsect()
    {
        setImageSets("WXDi-P16-084");

        setOriginalName("幻蟲　ミュウ//ディソナ");
        setAltNames("ゲンチュウミュウディソナ Genchuu Myuu Disona");
        setDescription("jp",
                "@C：対戦相手のレベル２以下のシグニの@E能力は発動しない。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Myu//Dissona, Phantom Insect");
        setDescription("en",
                "@C: The @E abilities of your opponent's level two or less SIGNI do not activate." +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Myu//Dissona, Phantom Insect");
        setDescription("en_fan",
                "@C: The @E abilities of your opponent's level 2 or lower SIGNI don't activate." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "幻虫 缪//失调");
        setDescription("zh_simplified", 
                "@C 对战对手的等级2以下的精灵的@E能力不能发动。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

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
            
            registerConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_ABILITY, TargetFilter.HINT_OWNER_OP, this::onConstEffModRuleCheck));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() instanceof EnterAbility && data.getSourceCardIndex() != null &&
                   CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                   data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() <= 2 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}

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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.game.FieldZone;

public final class LRIG_K3_NanashiPartThreeCalamity extends Card {

    public LRIG_K3_NanashiPartThreeCalamity()
    {
        setImageSets("WXDi-P13-010", "WXDi-P13-010U");

        setOriginalName("ナナシ　其ノ参ノ禍");
        setAltNames("ナナシソノサンノカ Nanashi Sono San no Ka");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが#Sの場合、対戦相手のシグニゾーン１つに【ウィルス】１つを置き、ターン終了時まで、対戦相手の感染状態のすべてのシグニのパワーを－3000する。\n" +
                "@A @[エクシード４]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。対戦相手のデッキの上からカードを６枚トラッシュに置く。"
        );

        setName("en", "Nanashi, Part Three Calamity");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are #S, put a [[Virus]] in one of your opponent's SIGNI Zones, and all infected SIGNI on your opponent's field get --3000 power until end of turn.\n@A @[Exceed 4]@: Target SIGNI on your opponent's field gets --10000 power until end of turn. Put the top six cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Nanashi, Part Three Calamity");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are #S @[Dissona]@ SIGNI, put 1 [[Virus]] onto 1 of your opponent's SIGNI zones, and until end of turn, all of your opponent's infected SIGNI get --3000 power.\n" +
                "@A @[Exceed 4]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power. Put the top 6 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "无名 其之叁之祸");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的全部的精灵是#S的场合，对战对手的精灵区1个放置[[病毒]]1个，直到回合结束时为止，对战对手的感染状态的全部的精灵的力量-3000。\n" +
                "@A @[超越 4]@:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。从对战对手的牌组上面把6张牌放置到废弃区。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+3);

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
            
            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getSIGNICount(getOwner()) > 0 && new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
            {
                FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI().not(new TargetFilter().infected())).get();
                attachZoneObject(fieldZone, CardUnderType.ZONE_VIRUS);
                
                gainPower(new TargetFilter().OP().SIGNI().infected().getExportedData(), -3000, ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
            
            millDeck(getOpponent(), 6);
        }
    }
}

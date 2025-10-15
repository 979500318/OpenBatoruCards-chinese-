package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K2_DeusMemoriaMediumEquipment extends Card {

    public SIGNI_K2_DeusMemoriaMediumEquipment()
    {
        setImageSets("WXDi-P11-080", "WXDi-P11-080P", "SPDi38-02");

        setOriginalName("中装　デウス//メモリア");
        setAltNames("チュウソウデウスメモリア Chuusou Deusu Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、このシグニの下からカードを好きな枚数トラッシュに置く。ターン終了時まで、それのパワーをこの方法でトラッシュに置いたカード１枚につき－3000する。\n" +
                "@E：あなたは手札を２枚までこのシグニの下に置く。"
        );

        setName("en", "Deus//Memoria, High Armed");
        setDescription("en",
                "@U: At the beginning of your attack phase, put any number of cards underneath this SIGNI into their owner's trash. Target SIGNI on your opponent's field gets --3000 power for each card put into the trash this way until end of turn.\n" +
                "@E: Put up to two cards from your hand under this SIGNI."
        );
        
        setName("en_fan", "Deus//Memoria, Medium Equipment");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and put any number of cards under this SIGNI into the trash. Until end of turn, it gets --3000 power for each card put into the trash this way.\n" +
                "@E: Put up to 2 cards from your hand under this SIGNI."
        );

		setName("zh_simplified", "中装 迪乌斯//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，从这只精灵的下面把牌任意张数放置到废弃区。直到回合结束时为止，其的力量依据这个方法放置到废弃区的牌的数量，每有1张就-3000。\n" +
                "@E :你把手牌2张最多放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0, AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.TRASH).own().under(getCardIndex()));
                gainPower(target, -3000 * trash(data), ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().fromHand());
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }
    }
}

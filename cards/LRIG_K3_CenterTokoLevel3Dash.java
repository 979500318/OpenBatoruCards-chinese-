package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CollaboLiverCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_CenterTokoLevel3Dash extends Card {

    public LRIG_K3_CenterTokoLevel3Dash()
    {
        setImageSets("WXDi-CP01-007");

        setOriginalName("【センター】とこ　レベル３'");
        setAltNames("センタートコレベルサンダッシュ Sentaa Toko Reberu San Dasshu Center Toko 3 Dash");
        setDescription("jp",
                "@E：コラボライバー２人を呼ぶ。\n" +
                "@A @[コラボライバー１人とコラボする]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "@A $G1 @[@|＃いぬいどんどんすきになる|@]@ %K0：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。その後、対戦相手のレベル１のシグニ１体と対戦相手のパワー8000以下のシグニ１体を対象とし、それらをバニッシュする。"
        );

        setName("en", "[Center] Toko, Level 3'");
        setDescription("en",
                "@E: Invite two Collab Livers.\n@A @[Collaborate with a Collab Liver]@: Target SIGNI on your opponent's field gets --8000 power until end of turn.\n@A $G1 #Inui_dondon_suki_ni_naru %K0: Target SIGNI on your opponent's field gets --5000 power until end of turn. Then, vanish target level one SIGNI on your opponent's field and target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "[Center] Toko Level 3'");
        setDescription("en_fan",
                "@E: Invite 2 CollaboLivers.\n" +
                "@A @[Collab with 1 CollaboLiver]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "@A $G1 @[@|#``LikingInuiMoreAndMore|@]@ %K0: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power. Then, target 1 of your opponent's level 1 SIGNI and 1 of your opponent's SIGNI with power 8000 or less, and banish them."
        );

		setName("zh_simplified", "【核心】床 等级3'");
        setDescription("zh_simplified", 
                "@E :呼唤联动主播2人。\n" +
                "@A 与联动主播1人联动:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "@A $G1 #越来越喜欢戌亥%K0:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。然后，对战对手的等级1的精灵1只和对战对手的力量8000以下的精灵1只作为对象，将这些破坏。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new CollaboLiverCost(1), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("#Liking Inui More and More");
        }

        private void onEnterEff()
        {
            inviteCollaboLivers(2);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }

        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());

            DataTable<CardIndex> data = new DataTable<>();
            target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
            if(target != null) data.add(target);
            target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            if(target != null) data.add(target);
            banish(data);
        }
    }
}
